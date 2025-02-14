import React from "react";
// css
import "./css/camp-list.css"
function Test(){
    return (
        <main id="main" className="camp-list">
            <section className="sec">
                <div className="inner_02">
                    <div className="serch_wrap">
                        <form action="" method="post" id="search-form">
                            <div className="search-filter">
                                <ul className="filter">
                                    <li className="item">
                                        전기
                                    </li>
                                    <li className="item">
                                        화장실
                                    </li>
                                    <li className="item">
                                        장작 판매
                                    </li>
                                    <li className="item">
                                        온수
                                    </li>
                                    <li className="item">
                                        와이파이
                                    </li>
                                    <li className="item">
                                        운동시설
                                    </li>
                                    <li className="item">
                                        반려동물
                                    </li>
                                    <li className="item">
                                        수영장
                                    </li>
                                </ul>
                            </div>
                            <div className="search-area mt_md">
                                <input type="text" className="search-input" placeholder="캠핑장 검색"/>
                                <input type="submit" value="" className="search-btn"/>
                            </div>
                        </form>
                        <div className="bar mt_lg"></div>
                    </div>
                </div>
            </section>
            <section className="sec">
                <div className="inner_02">
                    <div className="camp_list_wrap">
                        <ul className="camp_list">
                            <li className="camp">
                                <div className="wrap">
                                    <a href="./camp-detail.html">
                                        <div className="img-area">
                                            <img src="./images/camp/smaple_01.jpeg" alt="캠핑장 샘플"/>
                                            <div className="btn-box">
                                                <div id="like-btn" className="icon">
                                                    <img src="./images/camp/heart.svg" alt=""/>
                                                </div>
                                                <div id="share-btn" className="icon">
                                                    <img src="./images/camp/share.svg" alt=""/>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="txt-area">
                                            <h3 className="fs_lg mb_xsm">
                                                사천비토솔섬오토캠핑장
                                            </h3>
                                            <p className="fs_md">
                                                📍 경남 사천시 서포면 토끼로 245-29
                                            </p>
                                        </div>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
        </main>
    );
}

export default Test;