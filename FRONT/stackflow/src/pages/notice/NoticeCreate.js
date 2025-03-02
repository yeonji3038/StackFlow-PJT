import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styles from './Notice.module.css';

const BASE_URL = process.env.REACT_APP_API_URL;

const NoticeCreate = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [files, setFiles] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      const formData = new FormData();
      formData.append('title', title);
      formData.append('content', content);
      files.forEach(file => {
        formData.append('files', file);
      });

      await axios.post(`${BASE_URL}/api/notice/create`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        withCredentials: true
      });

      navigate('/notice');
    } catch (error) {
      console.error('공지사항 작성 실패:', error);
    }
  };

  return (
    <div className={styles.container}>
      <h2>공지사항 작성</h2>
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
        <div>
          <input
            type="file"
            multiple
            onChange={(e) => setFiles(Array.from(e.target.files))}
            className={styles.fileInput}
          />
        </div>
        <button type="submit" className={styles.button}>작성</button>
      </form>
    </div>
  );
};

export default NoticeCreate; 