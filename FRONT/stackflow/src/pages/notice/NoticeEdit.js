import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import styles from './Notice.module.css';

const BASE_URL = process.env.REACT_APP_API_URL;

const NoticeEdit = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  useEffect(() => {
    fetchNotice();
  }, [id]);

  const fetchNotice = async () => {
    try {
      const response = await axios.get(`${BASE_URL}/api/notice/${id}`, {
        withCredentials: true
      });
      setTitle(response.data.title);
      setContent(response.data.content);
    } catch (error) {
      console.error('공지사항 조회 실패:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`${BASE_URL}/api/notice/${id}`, {
        title,
        content
      }, {
        withCredentials: true
      });
      navigate('/notice');
    } catch (error) {
      console.error('공지사항 수정 실패:', error);
    }
  };

  return (
    <div className={styles.container}>
      <h2>공지사항 수정</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="text"
            placeholder="제목"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className={styles.input}
          />
        </div>
        <div>
          <textarea
            placeholder="내용"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className={styles.textarea}
          />
        </div>
        <button type="submit" className={styles.button}>수정</button>
      </form>
    </div>
  );
};

export default NoticeEdit; 