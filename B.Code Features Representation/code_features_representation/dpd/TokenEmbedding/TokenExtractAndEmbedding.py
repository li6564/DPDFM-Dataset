from transformers import AutoTokenizer, AutoModel
import javalang as jl
import javalang.tree as jlt
import os
import re
import json
import torch
class AstParser:
    def __init__(self, sPath, oPath):
        """
        程序的AST树解析
        :param sPath: 下载项目路径
        :param oPath: AST解析后的项目路径
        """
        self.sPath = sPath
        self.oPath = oPath

    def saveAstMap(self, proSource, reg=r'(.+).java$'):
        # print("local saveAstMap...")
        projectPath = self.sPath + proSource + "\\"
        projectJavaMap = {}
        for root, dirs, files in os.walk(projectPath):
            for each in files:
                tempPath = os.path.join(root, each)
                searchResult = re.search(reg, tempPath)
                if searchResult:
                    resultList = []
                    temp = searchResult.group(1)
                    tempJpath = '.'.join(temp.split("\\"))
                    try:
                        with open(tempPath, encoding='utf-8') as file:
                            JavaContents = file.read()
                    except Exception:
                        continue
                    try:
                        tree = jl.parse.parse(JavaContents)
                    except Exception:
                        continue
                    else:
                        for path, node in tree:
                            # 输出节点信息
                            if isinstance(node, jlt.PackageDeclaration):
                                print("PackageDeclaration:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.InterfaceDeclaration):
                                print("InterfaceDeclaration:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.ClassDeclaration):
                                resultList.append(str(node.name))
                                print("ClassDeclaration:", node.name)
                            elif isinstance(node, jlt.MethodDeclaration):
                                print("MethodDeclaration:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.ConstructorDeclaration):
                                print("ConstructorDeclaration:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.VariableDeclarator):
                                print("VariableDeclarator:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.FormalParameter):
                                print("FormalParameter:", node.name)
                                resultList.append(str(node.name))
                            elif isinstance(node, jlt.ReferenceType):
                                print("ReferenceType:", node.name)
                                resultList.append(str(node.name))
                        tempJpath=tempJpath.split(".")[-1]
                        projectJavaMap[tempJpath] = resultList[1:]

        return projectJavaMap

class AstFactory:
    def __init__(self,AstParser):
        self.AstParser=AstParser
    def astProcessing(self,Projects,reg):
        trainASPName = self.AstParser.saveAstMap(Projects, reg)
        return trainASPName

def getWord(path,trainASPName):
    with open(path, "w")as f:
        for i, k in trainASPName.items():
            for j in k:
                if k != "[]":
                    f.write(j)
                    f.write("  ")
            f.write("\n")

def tokenEmbedding(path, embeddingpath) :
    print("正在初始化token向量")
    projectJavaMap = {}
    # 预训练模型路径
    local_model_path = "E:/codebert-base"
    # 加载 CodeBERT 模型和 tokenizer
    tokenizer = AutoTokenizer.from_pretrained(local_model_path, local_files_only=True)
    model = AutoModel.from_pretrained(local_model_path, local_files_only=True, max_length=1000)
    # 打开文件
    with open(path, 'r') as file:
        # 读取文件的所有行
        lines = file.readlines()
        # 遍历每一行并打印
        for line in lines:
            list = []
            classname = get_first_n_words_as_string(line, 1)
            projectJavaMap[classname] = []
            line = get_first_n_words_as_string(line, 200)
            code_example = line.strip()  # 使用 strip() 方法去除每行末尾的换行符
            tokenized_code = tokenizer.encode_plus(code_example, return_tensors="pt", max_length=512)
            # 使用 CodeBERT 模型初始化 token 的嵌入向量
            with torch.no_grad():
                outputs = model(**tokenized_code)
            # 获取嵌入向量
            token_embeddings = outputs.last_hidden_state
            # 获取每个 token 的嵌入向量
            for i, token in enumerate(tokenized_code["input_ids"].squeeze()):
                list.append(token_embeddings[0][i].tolist())
            with open(embeddingpath + "tokenEmbedding" + ".json", "w") as f:
                projectJavaMap[classname] = list
                f.write(json.dumps(projectJavaMap))
    print("token向量初始化完成")
def get_first_n_words_as_string(s, n):
    words = s.split()[:n]  # 获取前 n 个单词
    return ' '.join(words)  # 将单词列表连接为一个字符串，以空格分隔

if __name__ == '__main__':
    data_in = "D:/dpdData/5 - JUnit v3.7"   #sourceCode path
    output_path = "../datasets/token/dataset/"      #tokenSequence output path
    embedding_path = '../datasets/token/dataset/embedding/'   #token embedding output path

    if not os.path.exists(output_path):
        os.makedirs(output_path)
    if not os.path.exists(embedding_path):
        os.makedirs(embedding_path)

    AstParser = AstParser(data_in, output_path)
    AstFactory = AstFactory(AstParser)

    trainASPName = AstFactory.astProcessing("", r'(.+).java$')
    getWord(output_path + "token", trainASPName)
    tokenEmbedding(output_path + "token", embedding_path)
