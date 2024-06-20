from neo4j import GraphDatabase
import csv

# 建立与数据库的连接
driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "123456"))
file_path = "../datasets/edge.csv"  # 生成文件路径
node_file_path = "../datasets/node.csv" #对应节点文件路径

# 创建会话
with driver.session() as session:
    # 执行查询语句，获取图数据
    result = session.run("MATCH (n)-[r]->(m) RETURN n, r, m ORDER BY n.node_id")

    # 创建CSV文件，并写入三元组数据
    with open(file_path, "w", newline="") as csvfile:
        writer = csv.writer(csvfile)

        # 写入CSV文件的表头
        writer.writerow(["Subject", "Object","Predicate"])

        # 遍历查询结果，转换为三元组并写入CSV文件
        for record in result:
            with open(node_file_path, "r") as file:
                reader = csv.reader(file)
                for row in reader:
                    if record["n"]["name"] == row[2]:
                        subject = row[0]
                    if record["m"]["name"] == row[2]:
                        object = row[0]
            predicate = record["r"].type
            if predicate == "EXTENDS":
                predicate = 2
            elif predicate == "IMPLEMENT":
                predicate = 2
            elif predicate == "ASSOCIATION":
                predicate = 5
            elif predicate == "INVOKING":
                predicate = 7
            elif predicate == "OVERRIDE":
                predicate = 11
            else: predicate = 0
            if predicate != 0:
                writer.writerow([subject, object, predicate])

# 关闭连接
driver.close()
