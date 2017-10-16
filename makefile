JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java
CLASSES =\
	decoder.java \
	FourWayHeap.java\
	BinaryHeap.java\
	PairHeap.java \
	TreeNode.java\
	PairNode.java \
	encoder.java 
MAIN = Encoder
default: classes
classes: $(CLASSES:.java=.class)
clean:
	find . -type f | xargs -n 5 touch
	$(RM) *.class
