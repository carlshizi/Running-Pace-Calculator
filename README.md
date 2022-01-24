# Running Pace Calculator EXTREME
With this calculator, you can create a race, add splits (split up your race and have different pace at different split points). You can redo and undo any action. You can output the current Miles/Hour stats. This was just a quick app I worked on. I might eventually add an interface with JavaFX.

A lot of logic went into calculating MPH to Pace conversion. I initially used a HashMap, storing the MPH in the key and the Pace in the value. I decided to use a simple method that takes the MPH and returns the pace (line 111 on Split.java).
I implemented an undo/redo using a Stack ADT.

![split calc1](https://user-images.githubusercontent.com/58310835/150028117-1a0528bf-f9b7-44e4-b0d6-6888bfd17248.png)
