import PlaceProfile from "./Place_profile";
import styles from "./chat_history.module.css";

export default function ChatHistory({ profileName, title, content, messageReceivedNum }) {
  return (
    <div className={styles.container}>
      <PlaceProfile name={profileName} />
      <div className={styles.container_box}>
        <span className={styles.container_title}>{title}</span>
        <span className={styles.container_content}>{content}</span>
      </div>
      {messageReceivedNum && (
        <div className={styles.container_message}>
          <img src="/blue_circle.svg" alt="메세지 온 갯수" />
          <span className={styles.container_message_num}>{messageReceivedNum}</span>
        </div>
      )}
    </div>
  );
}

