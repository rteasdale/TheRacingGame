/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car;

public class FinishLineType extends FixtureUserData{
    
    public int ID_Finish;

    public FinishLineType(int ID_Finish) {
        super(FixtureUserDataType.FUD_FINISH_LINE);
        
        this.ID_Finish = ID_Finish;
    }
    
}