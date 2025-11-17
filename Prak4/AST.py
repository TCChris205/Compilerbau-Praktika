class Interface:

    def getChildren(self):
        pass

class OPERATION(Interface):

    def __init__(self):
        super().__init__()
        self.type = ""
        self.expressions = [] 

    def getChildren(self):
        return self.expressions
    
class PRINT(Interface):
    
    def __init__(self):
        self.parameter = None
    
    def getChildren(self):
        return self.parameter
    
class STR(Interface):
    
    def __init__(self):
        self.parameter = []
    def getChildren(self):
        return self.parameter
    
class DEF(Interface):
    def __init__(self):
        self.name = ""
        self.value = None
    def getChildren(self):
        return (self.name, self.value)
    
class DEFN(Interface):
    def __init__(self):
        self.name = ""
        self.parameter = []
        self.expression = None
    def getChildren(self):
        return (self.name, self.parameter, self.expression)
    
class LET(Interface):
    def __init__(self):
        self.name = ""
        self.value = None
        self.expressions = []

    def getChildren(self):
        return (self.name, self.value, self.expressions)

class HEAD(Interface):
    def __init__(self):
        self.list = None
    def getChildren(self):
        return self.list
    
class TAIL(Interface):
    def __init__(self):
        self.list = None
    def getChildren(self):
        return self.list
    
class NTH(Interface):
    def __init__(self):
        self.list = None
        self.index = None
    def getChildren(self):
        return (self.list, self.index)
    
class IF(Interface):
    def __init__(self):
        self.condition = None
        self.ifPart = None
        self.elsePart = None
    def getChildren(self):
        return (self.condition, self.ifPart, self.elsePart)
    
class DO(Interface):
    def __init__(self):
        self.expressions = []
    def getChildren(self):
        return self.expressions
    
class STRING(Interface):
    def __init__(self):
        self.value = ""

    def getChildren(self):
        return None

    
class ID(Interface):
    def __init__(self):
        self.name = ""
    def getChildren(self):
        return None
    
class Bool(Interface):
    def __init__(self):
        self.value = None
        
    def getChildren(self):
        return None