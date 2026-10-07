package com.shanzhu.market.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.market.common.exception.BusinessException;
import com.shanzhu.market.entity.domain.Member;
import com.shanzhu.market.entity.query.QueryMember;
import com.shanzhu.market.mapper.MemberMapper;
import com.shanzhu.market.service.IMemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Override
    public Page<Member> queryPageByQo(QueryMember qo) {
        Page<Member> page = new Page<>(qo.getCurrentPage(), qo.getPageSize());
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(qo.getPhone()), "phone", qo.getPhone());
        wrapper.like(StringUtils.hasText(qo.getName()), "name", qo.getName());
        wrapper.eq(StringUtils.hasText(qo.getState()), "state", qo.getState());
        super.page(page, wrapper);
        return page;
    }

    @Override
    public void delMember(Long id) {
        UpdateWrapper<Member> wrapper = new UpdateWrapper<Member>()
                .set("state", Member.STATE_BAN)
                .set("integral", 0L)
                .eq("id", id);
        super.update(wrapper);
    }

    @Override
    public void saveMember(Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", member.getPhone()).eq("state", Member.STATE_NORMAL);
        Member one = super.getOne(wrapper);
        if (one != null) {
            throw new BusinessException("该用户已注册过");
        }
        member.setPassword(Member.DEFAULT_PWD);
        member.setState(Member.STATE_NORMAL);
        member.setIntegral(0L);
        super.save(member);
    }

    @Override
    public Member queryMemberById(Long id) {
        return super.getById(id);
    }

    @Override
    public void updateMember(Member member) {
        if (Member.STATE_NORMAL.equals(member.getState())) {
            QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", member.getPhone())
                    .ne("id",member.getId())
                    .eq("state", Member.STATE_NORMAL);
            Member one = super.getOne(wrapper);
            if (one != null) {
                throw new BusinessException("已被录入");
            }
        }
        super.updateById(member);
    }

    @Override
    public Member queryMemberByPhone(String phone) {
        QueryWrapper<Member> wrapper = new QueryWrapper<Member>().eq("phone", phone).eq("state", Member.STATE_NORMAL);
        Member one = super.getOne(wrapper);
        if (one==null){
            throw new BusinessException("该会员不存在");
        }
        return one;
    }
}
