package com.wxstc.dl;

import in.zapr.druid.druidry.Context;
import in.zapr.druid.druidry.Interval;
import in.zapr.druid.druidry.aggregator.CountAggregator;
import in.zapr.druid.druidry.aggregator.DoubleSumAggregator;
import in.zapr.druid.druidry.aggregator.DruidAggregator;
import in.zapr.druid.druidry.aggregator.LongSumAggregator;
import in.zapr.druid.druidry.client.DruidClient;
import in.zapr.druid.druidry.client.DruidConfiguration;
import in.zapr.druid.druidry.client.DruidJerseyClient;
import in.zapr.druid.druidry.client.DruidQueryProtocol;
import in.zapr.druid.druidry.client.exception.ConnectionException;
import in.zapr.druid.druidry.client.exception.QueryException;
import in.zapr.druid.druidry.dimension.DruidDimension;
import in.zapr.druid.druidry.dimension.SimpleDimension;
import in.zapr.druid.druidry.granularity.Granularity;
import in.zapr.druid.druidry.granularity.PredefinedGranularity;
import in.zapr.druid.druidry.granularity.SimpleGranularity;
import in.zapr.druid.druidry.query.aggregation.DruidTopNQuery;
import in.zapr.druid.druidry.topNMetric.SimpleMetric;
import in.zapr.druid.druidry.topNMetric.TopNMetric;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WikiData {
    public static void main(String[] args) throws ConnectionException, QueryException {
        /**
         * SelectorFilter selectorFilter1 = new SelectorFilter("dim1", "some_value");
         * SelectorFilter selectorFilter2 = new SelectorFilter("dim2", "some_other_val");
         *
         * AndFilter filter = new AndFilter(Arrays.asList(selectorFilter1, selectorFilter2));
         *
         * DruidAggregator aggregator1 = new LongSumAggregator("count", "count");
         * DruidAggregator aggregator2 = new DoubleSumAggregator("some_metric", "some_metric");
         *
         * FieldAccessPostAggregator fieldAccessPostAggregator1
         *         = new FieldAccessPostAggregator("some_metric", "some_metric");
         *
         * FieldAccessPostAggregator fieldAccessPostAggregator2
         *         = new FieldAccessPostAggregator("count", "count");
         *
         * DruidPostAggregator postAggregator = ArithmeticPostAggregator.builder()
         *         .name("sample_divide")
         *         .function(ArithmeticFunction.DIVIDE)
         *         .fields(Arrays.asList(fieldAccessPostAggregator1, fieldAccessPostAggregator2))
         *         .build();
         *
         * DateTime startTime = new DateTime(2013, 8, 31, 0, 0, 0, DateTimeZone.UTC);
         * DateTime endTime = new DateTime(2013, 9, 3, 0, 0, 0, DateTimeZone.UTC);
         * Interval interval = new Interval(startTime, endTime);
         *
         * Granularity granularity = new SimpleGranularity(PredefinedGranularity.ALL);
         * DruidDimension dimension = new SimpleDimension("sample_dim");
         * TopNMetric metric = new SimpleMetric("count");
         *
         *
         * {
         *   "queryType" : "topN",
         *   "dataSource" : "wikipedia",
         *   "intervals" : ["2015-09-12/2015-09-13"],
         *   "granularity" : "all",
         *   "dimension" : "page",
         *   "metric" : "count",
         *   "threshold" : 10,
         *   "aggregations" : [
         *     {
         *       "type" : "count",
         *       "name" : "count"
         *     }
         *   ]
         * }
         */
        DruidAggregator aggregator1 = new CountAggregator("count");
//        DruidAggregator aggregator2 = new DoubleSumAggregator("some_metric");
        TopNMetric metric = new SimpleMetric("count");
        Granularity granularity = new SimpleGranularity(PredefinedGranularity.ALL);
        DruidDimension dimension = new SimpleDimension("page");
        DateTime startTime = new DateTime(2013, 8, 31, 0, 0, 0, DateTimeZone.UTC);
        DateTime endTime = new DateTime(2019, 9, 3, 0, 0, 0, DateTimeZone.UTC);
        Interval interval = new Interval(startTime, endTime);
        DruidTopNQuery query = DruidTopNQuery.builder()
                .dataSource("wikipedia")
                .dimension(dimension)
                .threshold(10)
                .topNMetric(metric)
                .granularity(granularity)
//                .filter(filter)
                .aggregators(Arrays.asList(aggregator1))
//                .postAggregators(Collections.singletonList(postAggregator))
                .intervals(Collections.singletonList(interval))
                .build();

        DruidConfiguration config =  DruidConfiguration
                .builder()
                .host("scistor01")
                .protocol(DruidQueryProtocol.HTTP)
                .endpoint("druid/v2/")
                .build();

        DruidClient client = new DruidJerseyClient(config);
        client.connect();
        String query1 = client.query(query);
        System.out.println(query1);
        List<Result> responses = client.query(query, Result.class);
        responses.forEach(System.out::println);
        client.close();
    }
}
