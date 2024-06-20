import torch
import csv
import pickle
import json
import numpy as np

# 读取三元组文件
triples = []

# 读取CSV文件
entities =[]

edge_path = "datasets/edge.csv"
node_path = "datasets/node.csv"
embedding_path = "datasets/token/dataset/embedding/tokenEmbedding.json"

with open(edge_path, "r") as file:
    reader = csv.reader(file)
    next(reader)  # 跳过标题行
    for row in reader:
        triple = tuple(row)
        triples.append(triple)
with open(node_path, "r") as file:
    reader = csv.reader(file)
    next(reader)  # 跳过标题行
    for row in reader:
        entitie = tuple(row)
        entities.append(entitie)

# 提取边和边类型
edges = [(int(triple[0]), int(triple[1])) for triple in triples]
edge_types = [int(triple[2]) for triple in triples]
#提取得分
labels = [int(entitie[1]) for entitie in entities]
# 将边和边类型转换为张量
edges_tensor = (torch.tensor(edges, dtype=torch.int64).t()[0], torch.tensor(edges, dtype=torch.int64).t()[1])
edge_types_tensor = torch.tensor(edge_types, dtype=torch.int64)
#将标签转换为张量
labels_tensor = torch.tensor(labels, dtype=torch.int64)

with open(embedding_path, 'r') as f:
    json_str = f.read()
# 将 JSON 字符串解析为 Python 对象
parsed_data = json.loads(json_str)
# 创建包含数组的列表
sequences_vec = []
for entitie in entities:
    node_feature = []
    for key, value in parsed_data.items():
        flag = False
        if entitie[2].endswith(key):
            flag = True
            sequences_vec.append([np.array(v, dtype=np.double) for v in value])
            break
    if flag == False:
        vector = np.zeros(768, dtype=np.double)
        sequences_vec.append([np.array(vector, dtype=np.double) for v in range(1)])
max_len = max(len(seq) for seq in sequences_vec)
# 对序列进行填充
padded_sequences = torch.nn.utils.rnn.pad_sequence([torch.tensor(seq, dtype=torch.float32) for seq in sequences_vec], batch_first=True)
node_features_tensor = torch.tensor(padded_sequences)

#生成掩码
vector = []  # 存储生成的向量元素
for i in range(len(entities)):
    element = 0  # 根据需求生成向量的每个元素
    vector.append(element)  # 将元素添加到向量中
#生成掩码张量
invalid_masks_tensor = torch.tensor(vector)
# 输出转换后的张量形式
data_dict = {'edges': edges_tensor,
             'edge_types': edge_types_tensor,
             'labels': labels_tensor,
             'features': node_features_tensor,
             'invalid_masks': invalid_masks_tensor
             }

with open('datasets/data.pk', 'wb') as file:
    pickle.dump(data_dict, file)
# 从文本文件读取字典
with open('datasets/data.pk', 'rb') as file:
    restored_dict = pickle.load(file)
