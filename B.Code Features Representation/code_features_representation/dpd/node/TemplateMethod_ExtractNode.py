from neo4j import GraphDatabase
import csv

# 建立与数据库的连接
driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "123456"))
file_path = "../datasets/node.csv"  # 输入文件路径
# 创建会话
with driver.session() as session:
    # 执行查询语句，获取所有实体
    result = session.run("""
                MATCH (n)
                WHERE 
                  (n)-[:EXTENDS|IMPLEMENT|OVERRIDE]->() OR
                  ()-[:EXTENDS|IMPLEMENT|OVERRIDE]->(n)
                RETURN DISTINCT n
                ORDER BY n.node_id
            """)

    # 创建CSV文件，并写入实体数据
    with open(file_path, "w", newline="") as csvfile:
        writer = csv.writer(csvfile)
        # 写入CSV文件的表头
        writer.writerow(["node_id","label","Entity_name"])
        # 遍历查询结果，将实体及其属性写入CSV文件
        temp = 0
        for record in result:
            entity = record["n"]
            properties = dict(entity.items())
            node_id = temp
            temp += 1
            label = 0
            name = properties.get("name", None)  # 获取 name 属性
            writer.writerow([node_id,label,name])

# 关闭连接
driver.close()
