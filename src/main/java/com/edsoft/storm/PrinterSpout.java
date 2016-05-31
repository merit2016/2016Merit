package com.edsoft.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.edsoft.iot.Data;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by edsoft on 19.02.2016.
 */
public class PrinterSpout implements IRichSpout {

    protected SpoutOutputCollector collector;
    Map conf;
    TopologyContext context;
    Queue<Data> feedQueue = new LinkedList<Data>();
    Data[] feeds;

    public PrinterSpout(Data[] feeds) {
        this.feeds = feeds;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("feed"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.conf = conf;
        this.context = context;
        for (Data feed : feeds) {
            feedQueue.add(feed);
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void nextTuple() {
        Data nextFeed = feedQueue.poll();
        if (nextFeed != null) {
            collector.emit(new Values(nextFeed), nextFeed);
        }
    }

    @Override
    public void ack(Object msgId) {
        feedQueue.add((Data) msgId);
    }

    @Override
    public void fail(Object msgId) {
        feedQueue.add((Data) msgId);
    }
}
