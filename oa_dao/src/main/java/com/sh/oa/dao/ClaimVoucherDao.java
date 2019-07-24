package com.sh.oa.dao;

import com.sh.oa.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
//    新增一条报销单
    void insert(ClaimVoucher claimVoucher);
//    更新指定报销单信息
    void update(ClaimVoucher claimVoucher);
//    删除
    void delete(int id);
//    查询一条
    ClaimVoucher select(int id);
    List<ClaimVoucher> selectByCreateSn(String csn);
    List<ClaimVoucher> selectByNextDealSn(String ndsn);
}
