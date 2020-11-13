# atm
first setting the atm
 enter the initial capacity of the atm and the type of notes in it as input;
 
then by entering 1 0r 2 we get the condition of the atm i.e, start or off
only once.
  then we have to enter the account number  and  pin
    then we check whether he/she is a valid user or not.
    until the user is valid it repeats.
    then ifn the user is new he can create an account else they can access.
if the user is valid he can do changes he wants and the card should also be active to perform actions.
once the user is valid he/she gets the menu
by selecting 1 we get checkbalance which we can know the balance in that account;
by selecting 2 we get to withdrawal
   by checking conditions with limit
     then by the balance in account and the amount available in the dispenser he gets a message regarding withdrawal
     and amount will be deducted from account
by selecting 3 we get into deposit
   by checking conditions with limit we will deposit the amount
     and amount will be added to the account
by selecting 4 we get into transfer
     by checking conditions we can do money transfer
by selecting 5 we get into transfer
     the user will be exited

limitations..
  the accounts should already be there in the bank class
  the note type must be same for all the notes
   There is sql exception error occurs. ive written the  maximum code but somewhere there is some connection mistake(like more connections i tried but havent figured it out).if the sqlite error gets away the code runs for all cases.

atm as the major class links all the classes
transaction class which will have the minimum details for the transaction
checkbalance class extends transaction class and the method trans gives the balance in the account
withdrawal class extends transaction class and the method does the withdrwal of money by checking conditions
deposit class extends transaction class and the method does the depositing money in the account
account class which will have the information about the account of user
cashdispencer class which will have things about number of notes and type of notes in the dispenser

object oriented
 polymorphism,inheritance,encapsulation,abstraction
