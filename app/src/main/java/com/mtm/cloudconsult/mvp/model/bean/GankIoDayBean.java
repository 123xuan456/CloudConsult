package com.mtm.cloudconsult.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/24.
 */

public class GankIoDayBean implements Serializable {

    /**
     * category : ["Android","拓展资源","iOS","福利","休息视频"]
     * error : false
     * results : {"Android":[{"_id":"5c1372779d21223f5a2baeac","createdAt":"2019-01-03T06:14:37.194Z","desc":"图像风格迁移demo，基于tensorflow lite，功能不太完备，但是基本思路很有趣，用ipc实现tensor模块，一定程度上提高了对内存的容错率，避免OOM。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteamvvacj30f00qowqf","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteanw4d6j30f00qotkl","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteaon7g6j30f00qo46p","https://ww1.sinaimg.cn/large/0073sXn7ly1fyteaqiqbnj30f00qo7rd"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/MashirosBaumkuchen/Flora.git","used":true,"who":"冬"},{"_id":"5c14d4739d21223f60727cd1","createdAt":"2018-12-15T10:16:19.22Z","desc":"再也不用为手势操作忧虑啦， 旋转，缩放，移动一体化","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteb85l2wg30a00hsx6v"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/dinuscxj/MultiTouchGestureDetector","used":true,"who":"dinus_developer"},{"_id":"5c1704ee9d21223f60727cd5","createdAt":"2019-01-03T06:13:39.765Z","desc":"一个Android库，显示视图的占位符。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteb9s5hfg307i0dctpi"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/samlss/Broccoli","used":true,"who":"samlss"},{"_id":"5c2715339d212202c7b96974","createdAt":"2018-12-29T06:33:23.222Z","desc":"EasyPermission是一个简单易用，且无多余的第三方依赖的Android6.0动态权限申请库，支持链式调用，方便快捷。","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/panyiho/EasyPermission","used":true,"who":"Pan_"},{"_id":"5c28fa049d21224b2c82c3d5","createdAt":"2019-01-03T06:12:58.459Z","desc":"一个Android库，用于扫描和生成基于ZXing和ZBar的条形码。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteba64ltj30aq0juju9","https://ww1.sinaimg.cn/large/0073sXn7ly1fytebahwa1j30aq0ju41i","https://ww1.sinaimg.cn/large/0073sXn7ly1fytebaus0xj30aq0ju780"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"api","type":"Android","url":"https://github.com/wangpeiyuan/ZxingBarCode","used":true,"who":"null"},{"_id":"5c2b6f9c9d21224b35572843","createdAt":"2019-01-01T13:48:12.651Z","desc":"扰乱Touch事件分发机制，模仿支付宝首页下拉刷新动画","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebbuiw1j30u01o0k29"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"Android","url":"https://github.com/xmuSistone/AlipayPullRefresh","used":true,"who":"stone"},{"_id":"5c2da36b9d21226e02684c6f","createdAt":"2019-01-03T05:53:47.349Z","desc":"无需继承的Activity侧滑返回库 类全面屏返回手势效果 仿\u201c即刻\u201d侧滑返回。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebchnkvg305k09wgr3","https://ww1.sinaimg.cn/large/0073sXn7ly1fytebdwqh0g305k09w7iz"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"Android","url":"https://github.com/ParfoisMeng/SlideBack","used":true,"who":"lijinshanmx"},{"_id":"5c2da40c9d21226e02684c70","createdAt":"2019-01-03T05:56:28.289Z","desc":"仿iOS Siri波形的Android View。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebkucj2g30u01o01kz"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"Android","url":"https://github.com/koudle/AndroidSiriWave","used":true,"who":"lijinshanmx"},{"_id":"5c2da4419d21226e0983a128","createdAt":"2019-01-03T05:57:21.96Z","desc":"Android复杂红包雨实现，带点击事件处理。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebl6cj1j30qo1hcq4p"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"Android","url":"https://github.com/XunMengWinter/GiftRain","used":true,"who":"lijinshanmx"},{"_id":"5c2da48a9d21226e068debf4","createdAt":"2019-01-03T05:58:34.378Z","desc":"Android原生水球动画。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebllqelg30ak0irtbg"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"Android","url":"https://github.com/duldun/water-Ball","used":true,"who":"lijinshanmx"},{"_id":"5c2da5fd9d21226e0983a12a","createdAt":"2019-01-03T06:04:45.305Z","desc":"Android自定义控件RulerView，身高、体重等标尺，尺码控件，滑动可修改刻度值。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebo9hh8g30h40zk4qp"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"Android","url":"https://github.com/hnsycsxhzcsh/RulerView","used":true,"who":"lijinshanmx"}],"iOS":[{"_id":"5c2da9639d21226e00cb7697","createdAt":"2019-01-03T06:19:15.214Z","desc":"【ARKit】为您创建路径增强现实环境仅使用点来表示路径的中心。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytebzdbbjg307h0e81l1","https://ww1.sinaimg.cn/large/0073sXn7ly1fytec9ydlwg307j0e8kjo"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/maxxfrazer/ARKit-SCNPath","used":true,"who":"lijinshanmx"},{"_id":"5c2da9bb9d21226e0983a12c","createdAt":"2019-01-03T06:20:43.984Z","desc":"将您的Swift数据模型转换为可用的CRUD应用程序。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytecbe6pyj30f50qsqdk","https://ww1.sinaimg.cn/large/0073sXn7ly1fytecclbqvj30f50qsws7","https://ww1.sinaimg.cn/large/0073sXn7ly1fytecdq8ruj30f50qs4c8","https://ww1.sinaimg.cn/large/0073sXn7ly1fytecelcnhj30f50qs7dt"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/Q-Mobile/Model2App","used":true,"who":"lijinshanmx"},{"_id":"5c2da9e59d21226e02684c75","createdAt":"2019-01-03T06:21:25.955Z","desc":"使用Swift 4.2的新功能，像JavaScript一样动态访问JSON属性。","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/saoudrizwan/DynamicJSON","used":true,"who":"lijinshanmx"},{"_id":"5c2daa079d21226e0983a12e","createdAt":"2019-01-03T06:21:59.703Z","desc":"Folio Image Pipeline是iOS客户端的图像加载和缓存框架。","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/folio-sec/ImagePipeline","used":true,"who":"lijinshanmx"},{"_id":"5c2daa769d21226e068debf6","createdAt":"2019-01-03T06:23:50.760Z","desc":"一个可以发送消息和屏幕截图的App反馈。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fyteci29ftg30o40gmhdt"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/yahoojapan/AppFeedback","used":true,"who":"lijinshanmx"},{"_id":"5c2daac49d21226e068debf7","createdAt":"2019-01-03T06:25:08.456Z","desc":"一个流畅的左滑右滑卡片动画.","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytecvhn9ig30go0tnb2c"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/tigerAndBull/TABCardView","used":true,"who":"lijinshanmx"},{"_id":"5c2daaf29d21226e02684c76","createdAt":"2019-01-03T06:25:54.510Z","desc":"一个适用于iOS的Popover弹出标签。","images":["https://ww1.sinaimg.cn/large/0073sXn7ly1fytecxz85rg30r415w1kx"],"publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"iOS","url":"https://github.com/eonist/Popover-label","used":true,"who":"lijinshanmx"}],"休息视频":[{"_id":"5c2dabf89d21226e00cb7699","createdAt":"2019-01-03T06:30:16.909Z","desc":"##冬日恋歌 期盼着下雪❄️期盼着你 ","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"休息视频","url":"https://v.douyin.com/LYVFNT/","used":true,"who":"lijinshanmx"}],"拓展资源":[{"_id":"5c1498049d21223f57f1347e","createdAt":"2019-01-03T06:14:49.999Z","desc":"EventBus源码分析，看这一篇就够了！！\n","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"拓展资源","url":"https://blog.csdn.net/qq_34902522/article/details/85013185","used":true,"who":"宇宝守护神"},{"_id":"5c2d73069d212234e05d2b7e","createdAt":"2019-01-03T06:07:43.290Z","desc":"你的App正在裸奔！-- 关注Android应用安全","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"拓展资源","url":"https://mp.weixin.qq.com/s/Kz7XJPimggPJbk1iZ4g2RQ","used":true,"who":"JavaBoyHW"},{"_id":"5c2dac769d21226e068debfa","createdAt":"2019-01-03T06:32:22.513Z","desc":"做了2个多月的设计和编码，我梳理了Flutter动态化的方案对比及最佳实现。","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"拓展资源","url":"https://www.yuque.com/xytech/flutter/emdguh","used":true,"who":"lijinshanmx"},{"_id":"5c2dac9d9d21226e0983a131","createdAt":"2019-01-03T06:33:01.682Z","desc":"揭秘Flutter Hot Reload（原理篇）。","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"拓展资源","url":"https://www.yuque.com/xytech/flutter/uhw8vw","used":true,"who":"lijinshanmx"},{"_id":"5c2dace69d21226e00cb769a","createdAt":"2019-01-03T06:34:14.566Z","desc":"手把手教你编译Flutter engine。","publishedAt":"2019-01-03T00:00:00.0Z","source":"chrome","type":"拓展资源","url":"https://juejin.im/post/5c24acd5f265da6164141236","used":true,"who":"lijinshanmx"}],"福利":[{"_id":"5c2dabdb9d21226e068debf9","createdAt":"2019-01-03T06:29:47.895Z","desc":"2019-01-03","publishedAt":"2019-01-03T00:00:00.0Z","source":"web","type":"福利","url":"https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg","used":true,"who":"lijinshanmx"}]}
     */

    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
        private List<AndroidBean> Android;
        private List<AndroidBean> iOS;
        //解决json数据Key为中文
        @SerializedName("休息视频")
        private List<AndroidBean> restMovie;//休息视频
        @SerializedName("拓展资源")
        private List<AndroidBean> resource;//拓展资源
        @SerializedName("福利")
        private List<AndroidBean> welfare;//福利

        public List<AndroidBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<AndroidBean> android) {
            Android = android;
        }

        public List<AndroidBean> getiOS() {
            return iOS;
        }

        public void setiOS(List<AndroidBean> iOS) {
            this.iOS = iOS;
        }

        public List<AndroidBean> getRestMovie() {
            return restMovie;
        }

        public void setRestMovie(List<AndroidBean> restMovie) {
            this.restMovie = restMovie;
        }

        public List<AndroidBean> getResource() {
            return resource;
        }

        public void setResource(List<AndroidBean> resource) {
            this.resource = resource;
        }

        public List<AndroidBean> getWelfare() {
            return welfare;
        }

        public void setWelfare(List<AndroidBean> welfare) {
            this.welfare = welfare;
        }


    }
}
