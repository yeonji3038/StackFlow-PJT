import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styles from './Notice.module.css';

const BASE_URL = process.env.REACT_APP_API_URL;

const Notice = () => {
  const navigate = useNavigate();
  const [notices, setNotices] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchNotices();
  }, []);

  const fetchNotices = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/api/notice/list`, {
        withCredentials: true
      });
      setNotices(response.data);
    } catch (error) {
      console.error('공지사항 목록 조회 실패:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${BASE_URL}/api/notice/${id}`, {
        withCredentials: true
      });
      fetchNotices();
    } catch (error) {
      console.error('공지사항 삭제 실패:', error);
    }
  };

  const filteredNotices = notices.filter(notice =>
    notice.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>공지사항</h1>
      <div className={styles.divider} />
      
      <div className={styles.searchSection}>
        <div className={styles.searchBar}>
          <input
            type="text"
            placeholder="검색어를 입력하세요"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className={styles.searchInput}
          />
          <button className={styles.searchButton}>검색</button>
        </div>
        <button 
          className={styles.writeButton}
          onClick={() => navigate('/notice/create')}
        >
          글쓰기
        </button>
      </div>
      image.png
      <div className={styles.tableContainer}>
        <table className={styles.table}>
          <thead className={styles.tableHeader}>
            <tr>
              <th>NO</th>
              <th>제목</th>
              <th>등록일</th>
            </tr>
          </thead>
          <tbody className={styles.tableBody}>
            {filteredNotices.map((notice, index) => (
              <tr key={notice.id}>
                <td>{notices.length - index}</td>
                <td>{notice.title}</td>
                <td>{new Date(notice.createdAt).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className={styles.pagination}>
        <button className={styles.pageArrow}>≪</button>
        <button className={styles.pageArrow}>＜</button>
        <div className={styles.pageNumbers}>
          {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((num) => (
            <span 
              key={num} 
              className={`${styles.pageNumber} ${num === 1 ? styles.active : ''}`}
            >
              {num}
            </span>
          ))}
        </div>
        <button className={styles.pageArrow}>＞</button>
        <button className={styles.pageArrow}>≫</button>
        <select className={styles.dropdown}>
          <option>10개씩 보기</option>
          <option>20개씩 보기</option>
          <option>30개씩 보기</option>
        </select>
      </div>
    </div>
  );
};

export default Notice;