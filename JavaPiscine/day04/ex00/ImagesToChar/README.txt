mkdir target
javac -d target ./src/java/edu/school21/printer/app/Main.java ./src/java/edu/school21/printer/logic/ImageConverter.java
# java -cp target edu.school21.printer.app.Main <1> <2> <3>
# <1> - one symbol for black color
# <2> - one symbol for white color
# <3> - full or relative path to the .bmp picture
java -cp target edu.school21.printer.app.Main 0 . it.bmp