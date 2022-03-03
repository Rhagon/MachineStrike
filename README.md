#MachineStrike
###An implementation of the Machine Strike Mini game from Horizon: Forbidden West

---
#Concepts
- [Actions](#actions)
- [Rules](#rules)
- [Machines](#machines)


##Actions
An Action is something, that the user does, that influences the program.
When calling `execute()`, you have to pass a handler to it that does the actual work.
The idea is that not the action is the actor but the handler, that executes it.
The Action only describes what the handler should do.

###How to execute an Action
To execute an action, you simply have to call the action's `execute()` method with a valid handler.
Typically, a handler also provides an `execute()` method that takes an action, that it can handle,
and passes itself as handler to the action's `execute()` method.

###How to handle Actions with different handlers
Sometimes you have a producer, that creates actions that have to be handled differently.
The `InputHandler` of the Console Client for example produces `GameAction`s
as well as `ConsoleAction`s. In such a case you can use an `ActionUnion` to wrap them up
at creation and assign separate handlers to them later.

##Rules

##Machines