#MachineStrike
###An implementation of the Machine Strike Mini game from Horizon: Forbidden West

---
#Concepts
- [Actions](#actions)
- Rules
- Machines


##Actions
An Action is something, that the user does, that influences the game or something related to it.
What it does is up to the action and it's `execute()` method.
Typically, `execute()` will direct to a handler that does the actual work.
The idea is that not the action is the actor but the handler, that executes it.
The Action only describes that the handler should do.

###How to execute an Action
The intended way for executing an Action is the `Game.execute()` method. The game then passes itself
to the `Action.execute()` method that then redirects to the action handler. This allows Actions to be handled
in different ways, depending on the current state of the program.

You could also make a direct call to `Action.execute()` but then you have to make sure that you pass a game to it,
or that your action does not access the `Game` parameter. The `MoveAction` and `AttackAction` for example will always try
to access the parameter since the `Game` contains the handler for these Actions.
