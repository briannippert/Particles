/**
 * Chapter 7: AnimationTimer.java
 * A subclass of Timer that can be used for animation.
 * It also serves as an example of the code for an "event source" object.
 * Version 2 of 2
 */
import javax.swing.*;
import java.awt.event.*;

public class AnimationTimer extends javax.swing.Timer 
{
   private Animator _animator; // peer object
   
   public AnimationTimer( int anInterval, Animator a ) 
   {
      super( anInterval, null );
      _animator = a;
      this.addActionListener( new AnimationListener() );
   }
   
   private class AnimationListener implements ActionListener 
   {
      public void actionPerformed( ActionEvent e )
      {
         _animator.animate();
      }
   }
}

