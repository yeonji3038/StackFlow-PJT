import styles from "./PlaceProfile.module.css";

export default function PlaceProfile({ name = "" }) {
  return (
    <div className={styles.container}>
      <img src="/Avatar.svg" alt="PlaceProfile" />
      <span className={styles.container_text}>{name}</span>
    </div>
  );
}
