Serialization / Deserialization
(It is sometimes also referred to as marshalling / unmarshalling ..)

Ever wondered how applications keep data persistently? Well .. they save data to files. And they resume things by loading data from some place. This is for example how savegames in computer games work!

1.  Execute Savegame.java. How does it save your game? Try to comprehend the code, add new attributes to the savegame. And .. you did try to cheat, didn't you?! Why not set your level right to 2.147.483.101 by modifying the             savegame? Will you succeed?
    --> in a xml document with every property in a new file
    --> no, you won't succed

2.  Files. When speaking of files, which types do we typically distinguish in computer science? Which one is savegame.dat?

Guess what! When exchanging data with foreign applicatings .. one side has to serialize, the other one to deserialize the data. Welcome to IO, input output!

3.  In order to comprehend each other, communicating applications need a common language. The same language! In fact .. the same format when sharing data! Which data formats do you know? Where are the differences? Is HTML a data        exchange format? As a programmer, which formats would you prefer and why?