package machinestrike.game.machine;

public class MachineState {

    public boolean canMove;
    public boolean canAttack;
    public boolean wasOvercharged;

    public MachineState() {
        canMove = true;
        canAttack = true;
        wasOvercharged = false;
    }

}
