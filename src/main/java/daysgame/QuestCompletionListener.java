package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

public class QuestCompletionListener{
        public void finishQuest(){
                synchronized(this) {
                        notifyAll();
                }
        }
}
