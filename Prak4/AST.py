from Parser import Parser, Node
from Lexer import Lexer


class Interface:

    def isTerminal(self):
        return False
    def getChildren(self):
        pass
    def add(self):
        pass
    def toString(self):
        pass

class OPERATION(Interface):

    def __init__(self):
        super().__init__()
        self.expressions = [] 

    def getChildren(self):
        return self.expressions
    
    def add(self, Interface):
        self.expressions.append(Interface)
    def toString(self):
        s = "(OPERATION "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s
    
class CALL(Interface):
    def __init__(self):
        super().__init__()
        self.var = None
        self.expressions = [] 

    def getChildren(self):
        return self.expressions

    def add(self, Interface):
        if self.var is None:
            self.var = Interface
        else:
            self.expressions.append(Interface)
    
    def toString(self):
        s = "(CALL "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s
            
    
class PRINT(Interface):
    
    def __init__(self):
        self.parameter = None
    
    def getChildren(self):
        return self.parameter
    
    def add(self, Interface):
        if(self.parameter is None):
            self.parameter =Interface
        else: raise Exception("Print cant have more than one Parameter")
    
    def toString(self):
        return "(PRINT " + self.getChildren().toString() + ")"



    
class STR(Interface):
    
    def __init__(self):
        self.parameter = []
    def getChildren(self):
        return self.parameter
    def add(self, Interface):
        self.parameter.append(Interface)
    def toString(self):
        s = "(STR "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s
    
class DEF(Interface):
    def __init__(self):
        self.name = None
        self.value = None
    def getChildren(self):
        return [self.name , self.value]
    def add(self, Interface):
        if(self.name is None):
            self.name = Interface
        elif(self.value is None):
            self.value = Interface
        else: raise Exception("def can't have more than two Parameter")

    def toString(self):
        s = "(DEF "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s
    
class DEFN(Interface):
    def __init__(self):
        self.name = None
        self.expression = None
        self.parameter = []
    def getChildren(self):
        res = []
        res.append(self.name)
        res.append(self.expression)
        return res + self.parameter
    
    def add(self, Interface):
        if(self.name is None):
            self.name = Interface
        elif(self.expression is None):
            self.expression = Interface
        else:
            self.parameter.append(Interface)
    
    def toString(self):
        s = "(DEFN "
        for c in self.parameter:
            s += c.toString() + " "
        s += ": " + self.expression.toString()
        s += ")"
        return s
    
class LET(Interface):
    def __init__(self):
        self.name = None
        self.value = None
        self.expressions = []

    def getChildren(self):
        l = []
        l.append(self.name)
        l.append(self.value)
        return l + self.expressions
    
    def add(self, Interface):
        if(self.name is None):
            self.name = Interface
        elif(self.value is None):
            self.value = Interface
        else:
            self.expressions.append(Interface)

    def toString(self):
        s = "(Let "
        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s

class LIST(Interface):
    def __init__(self):
        self.expressions = []
    def getChildren(self):
        return self.expressions
    def add(self, Interface):
        self.expressions.append(Interface)

    def toString(self):
        s = "(List "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s

class HEAD(Interface):
    def __init__(self):
        self.list = None
    def getChildren(self):
        return self.list
    
    def add(self, Interface):
        if(self.list is None):
            self.list =Interface
        else: raise Exception("HEAD cant have more than one List")

    def toString(self):
        s = "(HEAD " + self.list.toString() + ")"
        return s
    
    
class TAIL(Interface):
    def __init__(self):
        self.list = None
    def getChildren(self):
        return self.list
    
    def add(self, Interface):
        if(self.list is None):
            self.list =Interface
        else: raise Exception("Tail cant have more than one List")

    def toString(self):
        s = "(TAIL " + self.list.toString() + ")"
        return s
    
class NTH(Interface):
    def __init__(self):
        self.list = None
        self.index = None
    def getChildren(self):
        l = []
        l.append(self.list)
        l.append(self.index)
        return l
    def add(self, Interface):
        if self.list is None:
            self.list = Interface
        elif self.index is None:
            self.index = Interface
        else:
            raise Exception("NTH can't have more than one list and one index")
        
    def toString(self):
        s = "(NTH "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s

    
class IF(Interface):
    def __init__(self):
        self.condition = None
        self.ifPart = None
        self.elsePart = None
    def getChildren(self):
        l = []
        l.append(self.condition)
        l.append(self.ifPart)
        l.append(self.elsePart)
        return l
    
    def add(self, Interface):
        if(self.condition is None):
            self.condition = Interface
        elif self.ifPart is None:
            self.ifPart = Interface
        elif self.elsePart is None:
            self.elsePart = Interface
        else: raise Exception("If cant have more than 3 Children")
    
    def toString(self):
        s = "(IF " + self.condition.toString() + " THEN " + self.ifPart.toString()
        if self.elsePart is None:
            s += ")"
        else:
            s += " ELSE " + self.elsePart.toString() + ")"
        return s
        
    
class DO(Interface):
    def __init__(self):
        self.expressions = []
    def getChildren(self):
        return self.expressions
    def add(self, Interface):
        self.expressions.append(Interface)
        
    def toString(self):
        s = "(DO "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s
    
class STRING(Interface):
    def isTerminal(self):
        return True
    
    def __init__(self, string):
        self.value = string

    def getChildren(self):
        return None
    def add(self, Interface):
        raise Exception("String can't have Children")
    def toString(self):
        return self.value

class NUM(Interface):
    def isTerminal(self):
        return True
    
    def __init__(self, value):
        self.value = value

    def getChildren(self):
        return None
    def add(self, Interface):
        raise Exception("Number can't have Children")
    def toString(self):
        return str(self.value)

class ID(Interface):

    def isTerminal(self):
        return True
    def __init__(self, name):
        self.value = name
    def getChildren(self):
        return None
    def add(self, Interface):
        raise Exception("ID can't have Children")
    def toString(self):
        return self.value
    
class COP(Interface):

    def isTerminal(self):
        return True
    def __init__(self, name):
        self.value = name
    def getChildren(self):
        return None
    def add(self, Interface):
        raise Exception("COP can't have Children")
    def toString(self):
        return self.value
    
class AOP(Interface):

    def isTerminal(self):
        return True
    def __init__(self, name):
        self.value = name
    def getChildren(self):
        return None
    def add(self, Interface):
        raise Exception("AOP can't have Children")
    def toString(self):
        return self.value
    
class Bool(Interface):
    def isTerminal(self):
        return True

    def __init__(self, value):
        self.value = value

    def getChildren(self):
        return None
    
    def add(self, Interface):
        raise Exception("Bool can't have Children")
    
    def toString(self):
        if self.value:
            return "True"
        return "False"
    
class Start(Interface):
    def __init__(self):
        self.expressions = []

    def getChildren(self):
        return self.expressions
    def add(self, Interface):
        self.expressions.append(Interface)

    def toString(self):
        s = "(Start "

        for c in self.getChildren():
            s += c.toString() + " "
        s += ")"
        return s


def StartAst(parseTree: Node)-> Interface:
    start = ToAst(parseTree, None)
    start.expressions.pop()
    return start

def ToAst(parseTree: Node,AST: Interface)-> Interface:
    match(parseTree.type):
        case "EOF": return None
        case "START":
            s = Start()
            for c in parseTree.children:
                s.add(ToAst(c,s))
            return s
            
        case "TRUE":
            return Bool(True)
        case "FALSE":
            return Bool(False)
        case "STRING":
            return STRING(parseTree.val)
        case "NUM":
            return NUM(parseTree.val)
        case "ID": 
            return ID(parseTree.val)
        case "COP": 
            return COP(parseTree.val)
        case "AOP": 
            return AOP(parseTree.val)
        case _: return proccesOther(parseTree,AST)

    
def proccesOther(parseTree: Node,AST: Interface) -> Interface:
    
    match(parseTree.type):
        case "EXPR":
            return ToAst(parseTree.children[0],AST)
        case "LITERAL":
            return ToAst(parseTree.children[0],AST)
        case "CALL":
            v = CALL()
            for c in parseTree.children:
                v.add(ToAst(c, v))
            return v
        case "PRINT":
            print = PRINT()
            for c in parseTree.children:
                print.add(ToAst(c,print))
            return print
        case "STR":
            str = STR()
            for c in parseTree.children:
                str.add(ToAst(c,str))
            return str
        case "DEF":
            v = DEF()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "DEFN":
            v = DEFN()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "LET":
            v = LET()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "LIST":
            v = LIST()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "HEAD":
            v = HEAD()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "TAIL":
            v = TAIL()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "NTH":
            v = NTH()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "IF":
            v = IF()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "DO":
            v = DO()
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v
        case "OPERATOR":
            v = OPERATION()
            v.type = parseTree.val
            for c in parseTree.children:
                v.add(ToAst(c,v))
            return v

        case _: raise Exception("Unexpected Node: " + parseTree.type)

if __name__ == "__main__":
    data = ""
    with open('Prak4/test.txt', 'r') as file:
        data = file.read()
    parser = Parser(data)
    parsTree = parser.start()
    AstTree = StartAst(parsTree)

    print(AstTree.toString())