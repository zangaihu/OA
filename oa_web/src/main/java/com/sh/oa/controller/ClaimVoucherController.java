package com.sh.oa.controller;

import com.sh.oa.biz.ClaimVoucherBiz;
import com.sh.oa.dto.ClaimVoucherInfo;
import com.sh.oa.entity.DealRecord;
import com.sh.oa.entity.Employee;
import com.sh.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Autowired
    private ClaimVoucherBiz claimVoucherBiz;
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
//      items， 传递报销理由到 add页面
        map.put("items", Contant.getItems());
//      创建一个新的报销单信息对象，添加页面的数据填充
        map.put("info",new ClaimVoucherInfo());
//        去添加页面
        return "claim_voucher_add";
    }
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info){
//      获取当前的用户
        Employee employee = (Employee)session.getAttribute("employee");
//        将处理人设置为自己，因为需要自己提交或者修改
        info.getClaimVoucher().setCreateSn(employee.getSn());
//        保存报销单的信息
        claimVoucherBiz.save(info.getClaimVoucher(),info.getItems());
//
        return "redirect:deal";
    }
    @RequestMapping("/detail")
    public String detail(int id, Map<String,Object> map){
//        根据id查询报销单信息，放入报销单对象
        map.put("claimVoucher",claimVoucherBiz.get(id));
//        查询报销单明细
        map.put("items",claimVoucherBiz.getItems(id));
//        查询出报销单处理记录
        map.put("records",claimVoucherBiz.getRecords(id));
//        返回到详情页面
        return "claim_voucher_detail";
    }
    @RequestMapping("/self")
    public String self(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherBiz.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String,Object> map){
//        从session中获取当前用户
        Employee employee = (Employee)session.getAttribute("employee");
//        根据employee的员工编号查询报销单明细
        map.put("list",claimVoucherBiz.getForDeal(employee.getSn()));
//        返回处理页面
        return "claim_voucher_deal";
    }

    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){
//        保需要的信息传递到更新页面
        map.put("items", Contant.getItems());
        ClaimVoucherInfo info =new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBiz.get(id));
        info.setItems(claimVoucherBiz.getItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }
    @RequestMapping("/update")
    public String update(HttpSession session, ClaimVoucherInfo info){
//        更新
        Employee employee = (Employee)session.getAttribute("employee");
        info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherBiz.update(info.getClaimVoucher(),info.getItems());
        return "redirect:deal";
    }
    @RequestMapping("/submit")
    public String submit(int id){
//        提交报销单等待处理
        claimVoucherBiz.submit(id);
        return "redirect:deal";
    }

    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBiz.get(id));
        map.put("items",claimVoucherBiz.getItems(id));
        map.put("records",claimVoucherBiz.getRecords(id));
//        创建一个处理记录的对象
        DealRecord dealRecord =new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }
    @RequestMapping("/check")
    public String check(HttpSession session, DealRecord dealRecord){
        Employee employee = (Employee)session.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());
        claimVoucherBiz.deal(dealRecord);
        return "redirect:deal";
    }
}
