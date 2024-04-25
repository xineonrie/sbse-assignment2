All of the three apps (GuiOptimiser, simpleApp and calculator) and the color.csv have to be in the same dir.

Color.csv file has the color (RGB) values for each GUI component.
e.g.
jTextField1,211,100,43
 // the background color of jTextField1
jTextField1TextColor,236,88,181 // the foreground (text) color of jTextField1

To run the GuiOptimiser app
java -jar GuiOptimiser.jar "the target app"
e.g. java -jar GuiOptimiser.jar simpleApp.jar


If you have any questions please post them on the course forum.

Good Luck,
Mahmoud.

# How to Start

1. Make sure you are at sbseAssignment2/GuiOptimiser/dist/simpleApp.jar
2. type in: java -jar GuiOptimiser.jar simpleApp.jar

# How to Compile

1. Make sure you are at sbseAssignment2/GuiOptimiser/
2. run: ant clean jar -Djavac.source=1.8 -Djavac.target=1.8
3. If simpleApp.jar or calculator.jar in dist is deleted, please find it back
4. and then you can start the project