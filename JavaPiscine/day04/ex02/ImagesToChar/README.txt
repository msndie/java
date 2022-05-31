mkdir lib
curl -s -o lib/JColor-5.3.1.jar https://repo1.maven.org/maven2/com/diogonunes/JColor/5.3.1/JColor-5.3.1.jar
curl -s -o lib/jcommander-1.78.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.78/jcommander-1.78.jar
mkdir -p target/resources
cp -R ./src/resources target/
jar xf lib/jcommander-1.78.jar com/
jar xf lib/JColor-5.3.1.jar com/
mv com target/
javac -cp target/ -d target/  src/java/edu/school21/printer/app/Main.java src/java/edu/school21/printer/logic/ImageConverter.java
jar cvmf ./src/manifest.txt ./target/images-to-chars-printer.jar -C target .
java -jar ./target/images-to-chars-printer.jar --white=RED --black=GREEN