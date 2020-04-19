/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Used to assign the value of variables without creating a reference to the original value
 * 
 * Variables:
 * 
 * E   -A generic, can be any type
 * 
 */
public class Pointer<E> {
        private E e;
        
        /**
         * @returns e
         */
        public E get() {
                return e;
        }
        
        /**
         * Sets the value of e to a new value
         * @param e The new value for e
         */
        public void set(E e) {
                this.e = e;
        }
}
