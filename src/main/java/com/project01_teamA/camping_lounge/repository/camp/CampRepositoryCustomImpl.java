package com.project01_teamA.camping_lounge.repository.camp;

import com.project01_teamA.camping_lounge.dto.response.camp.ResCampListDto;
import com.project01_teamA.camping_lounge.dto.response.camp.ResCampThumbUploadDto;
import com.project01_teamA.camping_lounge.entity.Campsite;
import com.project01_teamA.camping_lounge.entity.QCampThumbFiles;
import com.project01_teamA.camping_lounge.entity.QCampsite;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CampRepositoryCustomImpl implements CampRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResCampListDto> findFilteredCamps(String search, List<String> filters, Pageable pageable) {
        QCampsite camp = QCampsite.campsite;
        QCampThumbFiles thumb = QCampThumbFiles.campThumbFiles;
        BooleanBuilder builder = new BooleanBuilder();

        //검색기능
        if (search !=null && !search.isBlank()){
            builder.and(camp.campName.containsIgnoreCase(search)) //캠핑장 이름
                    .or(camp.campAddressDo.containsIgnoreCase(search)) //캠핑장 도
                    .or(camp.campAddressGungu.containsIgnoreCase(search)); //캠핑장 군,구
        }

        //필터 기능
        if (filters != null && !filters.isEmpty()){
            for(String filter : filters){
                switch (filter){
                    case "toilet": builder.and(camp.toilet.isTrue()); break;
                    case "hotwater": builder.and(camp.hotWater.isTrue()); break;
                    case "electric": builder.and(camp.electric.isTrue()); break;
                    case "firewood": builder.and(camp.fireWood.isTrue()); break;
                    case "wifi": builder.and(camp.wifi.isTrue()); break;
                    case "playGround": builder.and(camp.playGround.isTrue()); break;
                    case "pet": builder.and(camp.pet.isTrue()); break;
                    case "swimming": builder.and(camp.swimming.isTrue()); break;
                }
            }
        }

        //페이징
        List<Campsite> campList = queryFactory
                .select(camp)
                .leftJoin(camp.thumb, thumb).fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .from(camp)
                .fetch();

        long total = queryFactory
                .selectFrom(camp)
                .where(builder)
                .fetchCount();

        //DTO 전환
        List<ResCampListDto> dtoList = campList.stream()
                .map(c -> ResCampListDto.builder()
                        .id(c.getId())
                        .campName(c.getCampName())
                        .campInfo(c.getCampInfo())
                        .campAddressDo(c.getCampAddressDo())
                        .campAddressGungu(c.getCampAddressGungu())
                        .thumb(c.getThumb().stream()
                                .map(ResCampThumbUploadDto::fromEntity)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }
}
