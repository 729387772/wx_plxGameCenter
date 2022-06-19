package priv.gao.common.helper;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 文件描述
 * @date 2021/9/17 9:59
 */
public class ArrayUtils {

    public static void compareArr(String[] arr) {
        //利用冒泡排序法进行相关的排序
        for (int i = 0; i < arr.length; i++) {
            for(int j=0;j<arr.length-1-i;j++){
                // 比较方式是> 0 <0 这样的比较。
                if(arr[j].compareTo(arr[j+1])>0){
                    // 数组元素间进行调换
                    swapArr(arr,j,j+1);
                }
            }
        }
    }

    // 调换数组元素的值
    public static void swapArr(String []arr,int start,int end){
        String temp=arr[start];
        arr[start]=arr[end];
        arr[end]=temp;
    }

}
