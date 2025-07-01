# 📱 UITM Lecturer Schedule App – Development Log

---

## 🖌️ Design and Prototyping (FIGMA)
- ✅ **UI Design** – Visually appealing and user-friendly
- ✅ **UX Design** – Smooth navigation and flow
- 🔄 **Prototype Development** – Currently at **20%**

### 📷 Screenshot: Interface Design
![UI Screenshot](screenshot/interfacedesign.png)

---

## 💻 Development (Kotlin, Android Studio)
- 🔲 **Chosen Language**: Kotlin ✅ (planned)
- 🔲 **App Features**: UI, scheduling, etc.
- 🔲 **API Integration** (Retrofit + PHP, if needed)
- 🔲 **Regular Testing** and Debugging

---

## 🗄️ Database Design (ERD, MySQL, PHP)
- ✅ **ERD Created** – Based on required features
- ✅ **Normalized Tables** – No redundancy
- ✅ **Using MySQL** (via PHP backend)

### 📷 Screenshot: Database Schema
![ERD Screenshot](screenshot/mobileappdatabase.png)

---

## 🔐 User Authentication and Authorization
- 🔲 **Lecturer Login System** (email + password)
- 🔲 **Password Hashing** (SHA-256 or bcrypt)
- 🔲 **Session Management**
- 🔲 **User Roles & Permissions**
![API TESTING](screenshot/stupidapi.png)

---

## 🧠 User Interface and Experience
- 🔲 Prioritize user-friendly layouts (ongoing)
- 🔲 Conduct **usability testing** (peer feedback)
- 🔲 Ensure **accessibility** (text size, contrast, etc.)

---

## 📊 Progress Summary

| Module                         | Status       |
|--------------------------------|--------------|
| UI/UX Design (Figma)           | ✅ 80%        |
| App Feature Implementation     | 🔲 Starting   |
| Database Schema (ERD + SQL)    | ✅ Completed  |
| API + Backend (PHP + MySQL)    | 🔲 Not started|
| Authentication/Login System    | 🔲 Not started|
| Usability & Accessibility Test | 🔲 Not started|

---

## ✍️ Notes
> This is a final-year individual project for UITM, focused on helping new lecturers create and manage their class schedules easily using an Android mobile app.

---

## 🚧 To-Do Next:
- [ ] Build UI in Android Studio from Figma
- [ ] Implement login logic (Retrofit + PHP + MySQL)
- [ ] Store and retrieve schedule data via API


 ## ✍️ rEMINDER for navigation
 
 val registerNow = findViewById<TextView>(R.id.registerNow)
registerNow.setOnClickListener {
    val intent = Intent(this, RegisterActivity::class.java)
    startActivity(intent)
}
