package com.weirdo.server.utils;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * 二倍均值法
 * @ClassName: RedPacketUtils
 * @Author: 86166
 * @Date: 2020/3/21 16:33
 * @Description: chenLei
 */
public class RedPacketUtils {


    /**
     * 二倍均值法工具类
     * @param totalAmount 红包总金额(算法中的金额以 分 为单位)
     * @param totalPeople 抢红包的总人数
     * @return
     */
    public static List<Integer> divideRedPacket(final Integer totalAmount, final Integer totalPeople){

        List<Integer> list= Lists.newLinkedList();

        if (totalAmount>0 && totalPeople>0){
            Integer restAmount=totalAmount;
            Integer restPeople=totalPeople;

            Random random=new Random();
            int amount;
            for (int i=0;i<totalPeople-1;i++){
                //左闭右开 [1,剩余金额/剩余人数 的除数 的两倍 )

                amount=random.nextInt(restAmount/restPeople * 2 - 1)  + 1;
                list.add(amount);

                //剩余红包金额、随机红包金额  自减运算符，如：a-=b，等价于a=a-b。
                restAmount -= amount;
                //剩余人数
                restPeople--;
            }

            //最后一个剩余的金额
            list.add(restAmount);
        }
        return list;
    }



    public static void main(String[] args) {
        Integer amount=100;
        Integer people=10;

        List<Integer> list=divideRedPacket(amount,people);
        System.out.println("--红包拆分后的数组："+list);

        Integer total=0;
        for (Integer a:list){
            total+=a;
        }
        System.out.println("--红包总金额："+total);


    }
}
