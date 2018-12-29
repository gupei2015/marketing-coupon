package lk.project.marketing.backend.repository;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lk.project.marketing.backend.mapper.CouponReceiveManagementMapper;
import lk.project.marketing.base.entity.CouponReceive;
import org.springframework.stereotype.Repository;

@Repository
public class CouponReceiveManagementRepository extends ServiceImpl<CouponReceiveManagementMapper,CouponReceive> {

}
