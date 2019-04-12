package com.winbaoxian.module.example.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;

/**
 * @Author DongXL
 * @Create 2018-04-08 15:49
 * <p>
 * snowflake算法原理
 * <p>
 * snowflake生产的ID是一个18位的long型数字，二进制结构表示如下(每部分用-分开):
 * 0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000 - 00000 - 00000000 0000
 * <p>
 * 第一位未使用，接下来的41位为毫秒级时间(41位的长度可以使用69年，从1970-01-01 08:00:00)，然后是5位datacenterId（最大支持2^5＝32个，二进制表示从00000-11111，也即是十进制0-31），和5位workerId（最大支持2^5＝32个，原理同datacenterId），所以datacenterId*workerId最多支持部署1024个节点，最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生2^12＝4096个ID序号）.
 * <p>
 * 所有位数加起来共64位，恰好是一个Long型（转换为字符串长度为18）.
 * <p>
 * 单台机器实例，通过时间戳保证前41位是唯一的，分布式系统多台机器实例下，通过对每个机器实例分配不同的datacenterId和workerId避免中间的10位碰撞。最后12位每毫秒从0递增生产ID，再提一次：每毫秒最多生成4096个ID，每秒可达4096000个。理论上，只要CPU计算能力足够，单机每秒可生产400多万个，实测300w+，效率之高由此可见。
 */
public enum SnowflakeUtil {

    INSTANCE;

    private final long timeStampBaseLine = 1514736000000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    SnowflakeUtil() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        Map<String, String> properties = runtime.getSystemProperties();
        String sysDatacenterId = properties.get("snowflake.datacenterId");
        String sysWorkerId = properties.get("snowflake.workerId");
        if (StringUtils.isBlank(sysWorkerId)) {
            logger.warn("snowflake.datacenterId has not be setted, please set it");
        } else {
            datacenterId = Long.parseLong(sysDatacenterId);
        }
        if (StringUtils.isBlank(sysWorkerId)) {
            logger.warn("snowflake.workerId has not be setted, please set it");
        } else {
            workerId = Long.parseLong(sysWorkerId);
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            //服务器时钟被调整了,ID生成器停止服务.
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - timeStampBaseLine) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();

        long workerIdBits = 5L;
        long maxWorkerId = -1L ^ (-1L << workerIdBits);
        System.out.println(maxWorkerId);
    }

}
