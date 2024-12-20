import PlaceProfile from "./PlaceProfile";
import styles from "./chat_title.module.css";

export default function ChatTitle({ profileName, title, size }) {
  const titleClass = size === "large" ? styles.title_large : styles.title_small;
  return (
    <div className={styles.container}>
      <PlaceProfile name={profileName} />
      <span className={titleClass}>{title}</span>
    </div>
  );
}
