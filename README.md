# PrefixPostfixCalculator
<img src="/images/calculatorGUI.png" alt="Screenshot of the calculator GUI." width="600" height="600">
&nbsp;

Prefix/Postfix notation calculator created with JavaFX

# Installing
The JAR file ```PrefixPostfixCalc.jar``` is required to run the application.

# Running
The program can be run by double clicking on the JAR or from the command line using ```java -jar PrefixPostfixCalc.jar```.

# Overview
This Prefix/Postfix calculator application accepts compound mathematical expressions (either in prefix or postfix notation) from the user with support for maintaining operation history and variable bindings. Everything can be done with either mouse clicks or keyboard inputs.

The calculator must support the following arithmetic operators:
- Addition ```+```
- Subtraction ```-```
- Multiplication ```*```
- Exponentiation ```^```
- Division ```/```
- Modulo ```%```

Notations:
- Consider the given expression in infix notation ```5 + 10```. In prefix notation, is is written as ```+ 5 10```. In postfix notation, it is written as ```5 10 +```.

History:
- The calculator remembers the last expression it evaluated. The last used expression is displayed at the top of the history display. Clicking on the history display adds the expression to the input text field as well as marks it as the "last used" expression, essentially moving it to the top. Evaluating the same expression again also moves it to the top.
- Switching between notations also changes the notation of the expression in the history display.

<img src="./images/history.png" alt="Screenshot of history feature" width="600" height="600">

Variable Bindings:
- Users are able to add a variable binding by using the add binding section of the application next to the history display.
- Variable names can be anything but numbers, while values can only be numbers. You are unable to type in the incorrect thing in either text fields. Both text fields have to be filled to add a binding or it won't work.
- Clicking on a variable that is already created adds it to the input text field.
- You are able to reassign an existing variable by adding the existing variable with a different value.
  
<img src="./images/variableBindings.png" alt="Screenshot of variable binding" width="500" height="350">

# Architecture & Design Patterns
PrefixPostfixCalc uses a Model-View-Controller architecture pattern.

- The **Composite** pattern is used to store the compound expression as an expression tree. It's used to support a hierarchy of expression types, including binary expressions and constants (numbers).
- The **Strategy** pattern allows multiple modes (postfix or prefix) to be easily implemented and switching between them. Adding new parsing strategies is made much easier with it.
