# Txt2Automate - User Guide

---

## Description

Txt2Automate is a Kotlin project that allows you to create and manipulate finite automata from text files.
It also lets you visualize the automaton graph as a PNG using Graphviz.
The project works on Linux and Windows only.

---

## Text File Format

Each automaton is described in a .txt file with the following structure:

```
init e0
finals e0

stateA char char stateB

e0 0 3 6 9 e0
e0 1 4 7 e1

stateA char char stateB

alphabet digits
```

Keyword explanation:

- init: defines the initial state of the automaton (here e0)
- finals: lists the final states (here e0)
- alphabet: defines the allowed alphabet (letters for letters, digits for numbers, or specific characters)
- Other lines: define transitions
  - first column: source state
  - middle columns: values or symbols for the transition
  - last column: destination state
- A "sink" state is automatically used for undefined transitions, depending on your usage

---

## Installation and Running

Prerequisites:

- Kotlin (latest version)
- Java (latest version)
- Graphviz (latest version)
- The project should be located in the Txt2Automate directory

Linux:

1. Open a terminal in the project folder
2. Compile:

   kotlinc src -include-runtime -d automate.jar

3. Run:

   java -jar automate.jar

Windows:

- Compile and run using the terminal/cmd, similar to Linux
- Make sure Kotlin, Java, and Graphviz are installed and added to the PATH

---

## Usage

1. The program displays a menu with the list of automata available in src/automate/data/txt
2. Choose an automaton
3. Inside options:
   - Enter a word for validation
     - The program will return "OK" if the word is accepted or "KO" if not
     - The sink state is automatically used if the word leads to an undefined state
   - Print the graph
     - The program generates a .dot file in src/automate/data/dot
     - Graphviz automatically opens the corresponding PNG in src/automate/data/png

---

## Notes

- The automaton must be connected to be accepted
- The .dot and .png files are generated automatically
- This project only supports Linux and Windows
- Graphs with transitions containing too many values may be hard to read in the PNG
