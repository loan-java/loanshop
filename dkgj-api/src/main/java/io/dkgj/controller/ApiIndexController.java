package io.dkgj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.annotation.Login;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;
import io.dkgj.controller.vo.SortVO;
import io.dkgj.controller.vo.TypeVO;
import io.dkgj.entity.BannerEntity;
import io.dkgj.entity.ChannelManageEntity;
import io.dkgj.entity.LoanEntity;
import io.dkgj.entity.MarketEntity;
import io.dkgj.service.BannerService;
import io.dkgj.service.ChannelManageService;
import io.dkgj.service.LoanService;
import io.dkgj.service.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(tags = "首页接口")
public class ApiIndexController {

    @Autowired
    BannerService bannerService;

    @Autowired
    LoanService loanService;

    @Autowired
    MarketService marketService;

    @Autowired
    ChannelManageService channelManageService;

    public static Map<String, String> typeBasners = new HashMap<>();

    static {
        typeBasners.put("1", "http://testloan56.oss-cn-beijing.aliyuncs.com/card%402x.png");
        typeBasners.put("2", "http://testloan56.oss-cn-beijing.aliyuncs.com/high%402x.png");
        typeBasners.put("3", "http://testloan56.oss-cn-beijing.aliyuncs.com/low%402x.png");
        typeBasners.put("4", "http://testloan56.oss-cn-beijing.aliyuncs.com/fast%402x.png");
    }

    @GetMapping("getChannelEnable")
    @ApiOperation("渠道是否可用")
    public R getChannelEnable(@RequestParam String channel) {
        ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>()
                .eq("channelcode", channel).eq("state", 0));
        return R.ok().put("data", channelManageEntity == null);
    }


    @GetMapping("index")
    @ApiOperation("首页接口")
    @Login
    public R index(@ApiIgnore @RequestAttribute("userId") Integer userId,
                   @RequestParam(required = false, defaultValue = "") String channel,
                   @RequestParam(required = false, defaultValue = "") String version) {

        MarketEntity market = marketService.getOne(new QueryWrapper<MarketEntity>()
                .eq("state", 1)
                .eq("channel_code", channel)
                .eq("version", version));

        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "4");

        PageUtils page = bannerService.queryPage(params);
        List<BannerEntity> banners = (List<BannerEntity>) page.getList();

        List<TypeVO> types = new ArrayList<TypeVO>();
        types.add(new TypeVO(1, "身份证贷", 1));
        types.add(new TypeVO(2, "高通过率", 2));
        types.add(new TypeVO(3, "超低门槛", 3));
        types.add(new TypeVO(4, "极速下款", 4));

        params.put("page", "1");
        params.put("limit", "1000");
        params.put("sidx", "weights");
        params.put("order", "asc");

        if (market != null) {
            params.put("market", market.getChannelCode());
        }

        page = loanService.queryPage(params);
        List<LoanEntity> loans = (List<LoanEntity>) page.getList();

        Map<String, Object> result = new HashMap<>();
        if (market != null) {
            result.put("banners", null);
        } else {
            result.put("banners", banners);
        }
        result.put("types", types);
        result.put("loans", loans);
        return R.ok().put("data", result);
    }

    @GetMapping("getLoanList")
    @ApiOperation("贷款大全接口")
    @Login
    public R getLoanList(@ApiIgnore @RequestAttribute("userId") Integer userId, @RequestParam Map<String, Object> params,
                         @RequestParam(required = false, defaultValue = "") String channel,
                         @RequestParam(required = false, defaultValue = "") String version) {
        MarketEntity market = marketService.getOne(new QueryWrapper<MarketEntity>()
                .eq("state", 1)
                .eq("channel_code", channel)
                .eq("version", version));
        if (market != null) {
            params.put("market", market.getChannelCode());
        }
        PageUtils page = loanService.queryPage(params);
        return R.ok().put("data", page);
    }


    @GetMapping("getTypeLoanList")
    @ApiOperation("贷款类型接口")
    @Login
    public R getTypeLoanList(@ApiIgnore @RequestAttribute("userId") Integer userId, @RequestParam Map<String, Object> params,
                             @RequestParam(required = false, defaultValue = "") String channel,
                             @RequestParam(required = false, defaultValue = "") String version) {
        MarketEntity market = marketService.getOne(new QueryWrapper<MarketEntity>()
                .eq("state", 1)
                .eq("channel_code", channel)
                .eq("version", version));
        if (market != null) {
            params.put("market", market.getChannelCode());
        }

        PageUtils page = loanService.queryPage(params);

        page.setTypeBanner(typeBasners.get((String) params.get("type")));

        return R.ok().put("data", page);
    }

    @GetMapping("getRandomLoan")
    @ApiOperation("贷款类型接口")
    @Login
    public R getRandomLoan(@RequestParam Integer limit) {
        List<LoanEntity> list = loanService.getRandomLoan(limit);
        return R.ok().put("data", list);
    }

    @GetMapping("getFiltrate")
    @ApiOperation("获取筛选条件")
    @Login
    public R getFiltrate(@ApiIgnore @RequestAttribute("userId") Integer userId) {
        List<SortVO> moneyFiltrate = new ArrayList<>();
        List<SortVO> typesortFiltrate = new ArrayList<>();
        List<SortVO> tagsFiltrate = new ArrayList<>();

        moneyFiltrate.add(new SortVO("0", "金额不限", 1));
        moneyFiltrate.add(new SortVO("1", "2000以下", 2));
        moneyFiltrate.add(new SortVO("2", "2000-5000", 3));
        moneyFiltrate.add(new SortVO("3", "5000以上", 4));

        typesortFiltrate.add(new SortVO("", "默认排序", 1));
        typesortFiltrate.add(new SortVO("HIGH", "贷款额度高", 2));
        typesortFiltrate.add(new SortVO("LOW", "贷款利率低", 3));
        typesortFiltrate.add(new SortVO("FAST", "放款速度快", 4));

        tagsFiltrate.add(new SortVO("", "全部", 1));
        tagsFiltrate.add(new SortVO("RECOMMENDED", "推荐", 2));
        tagsFiltrate.add(new SortVO("HOT", "爆款", 3));
        tagsFiltrate.add(new SortVO("OPTIMIZATION", "优选", 4));

        Map<String, Object> result = new HashMap<>();
        result.put("moneyFiltrate", moneyFiltrate);
        result.put("typesortFiltrate", typesortFiltrate);
        result.put("tagsFiltrate", tagsFiltrate);

        return R.ok().put("data", result);

    }

}
