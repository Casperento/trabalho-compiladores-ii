//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package gj.lang.reflect;

class Array {
    Array() {
    }

    public static Object[] newInstance(Object[] var0, int var1) {
        return (Object[])java.lang.reflect.Array.newInstance(var0.getClass().getComponentType(), var1);
    }
}
