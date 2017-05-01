# CompilerLab
Compiler lab, Spring 2017


1400012858 贾连成

jlc@pku.edu.cn

### 类型检查中的一点说明：
* 输入文档是一个文件，可在TypeCheck.java中的Main函数中修改，默认为testcases/extend_test.java
* 对于类型检查中的大部分错误，会输出出错语句的大概位置(精确到行)
* 允许所需类型为父类，提供类型为子类的情形
* 出错时会throw一个RuntimeException，否则输出