package com.shanzhu.market.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.market.entity.domain.Member;
import com.shanzhu.market.entity.query.QueryMember;

public interface IMemberService extends IService<Member> {
    Page<Member> queryPageByQo(QueryMember qo);

    void delMember(Long id);

    void saveMember(Member member);

    Member queryMemberById(Long id);

    void updateMember(Member member);

    Member queryMemberByPhone(String phone);
}
