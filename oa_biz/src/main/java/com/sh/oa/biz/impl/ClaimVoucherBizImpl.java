package com.sh.oa.biz.impl;

import com.sh.oa.biz.ClaimVoucherBiz;
import com.sh.oa.dao.ClaimVoucherDao;
import com.sh.oa.dao.ClaimVoucherItemDao;
import com.sh.oa.dao.DealRecordDao;
import com.sh.oa.dao.EmployeeDao;
import com.sh.oa.entity.ClaimVoucher;
import com.sh.oa.entity.ClaimVoucherItem;
import com.sh.oa.entity.DealRecord;
import com.sh.oa.entity.Employee;
import com.sh.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service("claimVoucherBiz")
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {


    @Autowired
    private ClaimVoucherDao claimVoucherDao;


    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;

    @Autowired
    private DealRecordDao dealRecordDao;

    @Autowired
    private EmployeeDao employeeDao;


/*
* 保存报销单信息
* id 自动生成
* create-sn  创建人编号，传值
* total-ammount  总金额，传值
* cause   理由， 传值
* create-time 创建时间 ，设置
* nextdeal  自己设置
* 状态 设置
* */
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
//      设置当前报销时间
        claimVoucher.setCreateTime(new Date());
//      设置下一个处理人，新创建的报销单设置为自己，等待下一步提交
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
//      设置当前状态（新创建）
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
//        插入一条报销单信息
        claimVoucherDao.insert(claimVoucher);
//         遍历传过来的items，全部插入报销单明细表
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }


    /*获取报销单信息*/
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }
    /*根据报销单id获取报销明细的信息*/
    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }
    /*根据报销单id获取处理记录*/
    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }
    /*根据创建人获取报销单信息，以便进一步的提交和修改*/
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }
    /*根据下个处理人的工号获取，让他去处理报销单*/
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    /*更新报销单*/
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for(ClaimVoucherItem old:olds){
            boolean isHave=false;
            for(ClaimVoucherItem item:items){
                if(item.getId()==old.getId()){
                    isHave=true;
                    break;
                }
            }
//            删除旧信息
            if(!isHave){
                claimVoucherItemDao.delete(old.getId());
            }
        }
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
//            没有就新增
            if(item.getId()!=null){
                claimVoucherItemDao.update(item);
            }else{
//                否则插入
                claimVoucherItemDao.insert(item);
            }
        }

    }
    /*提交报销单*/
    public void submit(int id) {
//        先查询出报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
//        修改器状态，改为提交
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
//        设置下一个处理人，为所在部门的部门经理
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);

//      创建一条处理记录
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);
    }

    public void deal(DealRecord dealRecord) {
//        查询出需要处理的报销单，和处理人
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        Employee employee = employeeDao.select(dealRecord.getDealSn());
//        设置处理时间
        dealRecord.setDealTime(new Date());


        /*
        *
        * 判断处理状态 和 报销单的金额总数，交给部门经理审核
        * 金额过多，需要总经理复审
        *
        *
        *
        * */

        /*
        * 处理方式为通过，需要考虑
        * 1.不需要复审
        *
        * 2.金额多，需要复审
        *
        * */
        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
//            /*金额较小，或者当前处理人为总经理，不需要复审*/
            if(claimVoucher.getTotalAmount()<=Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){

//              设置为 已审核
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
//               设置下一个处理人为财务，等待打款操作
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{
//                否则，需要复审，下个处理人为总经理
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getSn());
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
//            处理方式为打回，下个处理人为创建者，需要更改报销单
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
//            处理方式为拒绝，则不通过，报销作废
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
//            处理方式为打款，设置状态为已打款
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }
//更新信息
        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }

}
