# Multithreading-Problem
Classes and their use::

1)DBManipulation:
          This class is used to do all manipulations from database.
          -creating DB connection
          -reading Status from DB
          -updating status of files
          
  2)StatusChange:
          This is the thread processing the files and changing its status.
          
  3)Status:
          The main Class.
          
          
DataBase Table:
Table name: File
Atrributes:
1)id
2)filename
3)file(not used in this code)
4)status

