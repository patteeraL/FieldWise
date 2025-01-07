# **FieldWise | Language Learning Application**

**FieldWise** is an **Android language learning application**, developed in **Kotlin**, designed to enhance the process of acquiring **technical and field-specific terminology**. With FieldWise, learners gain language skills tailored to their professional or academic pursuits.
<br>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/0cac93273c14ffd0f4227fedbe4325c4a2c50809/app/src/main/ic_launcher-playstore.png" width="300"/></p>
<p align="center"><i><b>Figure 1:</b> App Icon </i></p><br>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/SplashScreen%20of%20FieldWise.png" width="300"/></p>
<p align="center"><i><b>Figure 2:</b> Splash Screen of FieldWise</i></p>
<br>

# **Information**
**FieldWise** is not like other language apps. It’s different because it focuses on something special—helping people learn the kind of language they need for their **specific jobs** or **studies**. Apps like Duolingo, Babbel, or Rosetta Stone only teach the basics, but they don’t work for people who need **professional** or **technical language**.

## **Why Choose FieldWise?**

### **Unique Features**
1. **Tailored Learning**  
   - Industry-specific lessons to improve real-world communication skills.  
2. **Community Learning**  
   - Collaborate with peers for enhanced motivation and engagement.  
3. **Comprehensive Learning Styles**  
   - Exercises for all skill areas: speaking, listening, vocabulary, and conversations.  
4. **AI Feedback**  
   - Instant feedback and tips to improve accuracy and fluency.  

### **What’s Missing in Other Apps?**
- Lack of **technical vocabulary** for specialized professions.
- Minimal focus on **real-world simulations** like professional presentations or negotiations.

# User Story | Smartphone Demonstration
<table>
  <thead>
    <tr>
      <th>User Story</th>
      <th colspan="2" align="center">Preview</th>
      <th colspan="3" align="center">Diagram</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><b>Profile Creation</b></td>
      <td colspan="2" align="center">
        <img src="https://github.com/olala10/FieldWise/blob/4455654f3342cd71cb3d3a7dc67e9440ba5aa757/resourse/User%20story%20of%20profile_creation.gif" width="300" alt="Profile Creation Preview"/>
      </td>
      <td colspan="3" align="center">
        <img src="https://github.com/olala10/FieldWise/blob/dc9f3d07fb535c54187390d93b1e541926fdce5a/resourse/User%20story%20of%20profile%20creation%20diagram.png" width="1200" alt="Profile Creation Diagram"/>
      </td>
    </tr>
    <tr>
      <td><b>Profile Preference</b></td>
      <td colspan="2" align="center">
        <img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/User%20story%20of%20profile_preference%20.gif" width="300" alt="Profile Preference Preview"/>
      </td>
      <td colspan="3" align="center">
        <img src="https://github.com/olala10/FieldWise/blob/dc9f3d07fb535c54187390d93b1e541926fdce5a/resourse/User%20story%20of%20profile%20preference%20diagram.png" width="1200" alt="Profile Preference Diagram"/>
      </td>
    </tr>
    <tr>
      <td><b>Lessons (Speaking, Listening, Vocabulary, Conversation Exercises)</b></td>
      <td colspan="2" align="center">
        <p>
          <img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/User%20story%20of%20lessons%20(Speaking).gif" width="300" alt="Speaking Exercise Preview"/><br/>
          <img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/User%20story%20of%20lessons%20(Listening).gif" width="300" alt="Listening Exercise Preview"/><br/>
          <img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/User%20story%20of%20lessons%20(Vocab).gif" width="300" alt="Vocabulary Exercise Preview"/><br/>
          <img src="https://github.com/olala10/FieldWise/blob/edbc7dfab8cb0421410ddc038fd9c163fd065ca9/resourse/User%20story%20of%20lessons%20(Conversation).gif" width="300" alt="Conversation Exercise Preview"/>
        </p>
      </td>
      <td colspan="3" align="center">
        <img src="https://github.com/olala10/FieldWise/blob/dc9f3d07fb535c54187390d93b1e541926fdce5a/resourse/User%20story%20of%20lessons%20diagram.png" width="1200" alt="Lessons Diagram"/>
      </td>
    </tr>
  </tbody>
</table>

## **Technical Details**
### **Modules**
1. **Profile Creation Module**: Tracks user progress and stores it in the cloud.  
2. **Course Management Module**: Users select languages and specialized courses.  
3. **Exercise Module**: Includes exercises for speaking, listening, vocabulary, and conversations.  
4. **Leaderboard Module**: Tracks global streak rankings.  
5. **Social Interaction Module**: Discussion forums for exercises.  
6. **AI for Feedback**: Real-time corrections and conversational AI support.  

### **FieldWise Architecture**
<p align="center">
  <img src="https://github.com/olala10/FieldWise/blob/main/resourse/FieldWise%20Architectural%20Diagram.png" width="1000" alt="Architectural Diagram"/>
</p>
<p align="center"><i><b>Figure 3:</b> Architectural Diagram</i></p>

### **Backend**
1. Fieldwise **AI Backend** implements the AI conversations, audio transcription and text-to-speech features:</br>
[FieldWise AI Backend Repository](https://github.com/immagiov4/FieldWise_Backend)
2. Firebase's Realtime Database and Storage are used in the app for the database of users and exercises and storage of media files respectively.<br>
[FieldWise Firebase Media Resources](https://chula-my.sharepoint.com/:f:/g/personal/6538088321_student_chula_ac_th/Es2QzwREkllFqVph5ogcYUQBLH36FmkkBMcU6pCat6cm0w?e=1UABcN) <br>
[FieldWise Firebase Reatime Database Default File](https://github.com/olala10/FieldWise/blob/main/resourse/FieldWise%20Defaut%20Database%20(For%20Firebase%20Realtime%20Database).json) <br>
<I>Note: The google-services.json file is not included in this repository for security reasons. Each user must provide their own configuration file.</i>
