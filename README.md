# FieldWise | Language Learning Application
<p>FieldWise is an Android language learning mobile application developed in Kotlin, designed to simplify and enhance the process of learning new languages. FieldWise offers the functionality to learn  technical and field-specific terminology, enabling learners to acquire language skills directly applicable to their professional or academic pursuits.</p>
<br>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/0cac93273c14ffd0f4227fedbe4325c4a2c50809/app/src/main/ic_launcher-playstore.png" width="300"/></p>
<p align="center"><i><b>Figure 1</b> App Icon </i></p><br>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/b4da3890986c1cbbaada11684b43f4d335485c41/resourse/SplashScreen%20of%20FieldWise.png" width="300"/></p>
<p align="center"><i><b>Figure 2</b> Splash Screen of FieldWise</i></p>
<br>

# Information
<p>FieldWise is not like other language apps. It’s different because it focuses on something special—helping people learn the kind of language they need for their specific jobs or studies. Apps like Duolingo, Babbel, or Rosetta Stone only teach the basics, but they don’t work for people who need professional or technical language.</p>
<p>Why FieldWise is Special?</p>
<ul>
  <li>Learning for Specific Fields: FieldWise gives lessons with the exact words and phrases people need in jobs like engineering, business, or academic research.</li>
<li>Learning Together: You can make or join groups with others who have the same goals as you. It makes learning more fun, and you’ll stay motivated because you’re not alone.</li>
Different Ways to Learn: People learn in different ways. FieldWise uses many teaching styles to make it easier for everyone to understand.</li>
<li>AI That Helps You Improve: The app’s smart AI checks your answers instantly and gives you tips to get better.</li></ul>
<p>Most apps are not good at teaching:</p>
<ul>
<li>Technical Words: You won’t find lessons for industry or professional language in most apps.</li>
<li>Practice for Real-Life Situations: They don’t give you simulations, like how to present in meetings or negotiate in a professional way.</li>
</ul></p>

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

# FieldWise’s Context  & Data
<p>The context diagram below shows the data flow and technical details of the app</p>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/main/resourse/FieldWise%20Context%20Diagram.png" width="500"/></p>
<p align="center"><i><b>Figure 3</b> FieldWise Context Diagram</i></p>
<br>
<p>FieldWise main modules are:</p>
<ul>
<li>Profile Creation Module - Tracks user progress and stores data in the cloud database.</li>
<li>Course Management Module - User selects language and specialized courses. Each course consists of a set of lessons including different types of exercises displayed in the chosen language. Each lesson is a set of exercises of various types, and each one has a correct answer associated.</li>
<li>Exercise Module - Includes speaking, listening, vocabulary, and conversation.</li>
<li>Profile and Leaderboard Module - Shows individual profile and shows global streak leaderboard.</li>
<li>Social Interaction Module -  Users can join in discussions for each exercise.</li>
<li>AI for Conversation - Uses AI for conversation exercise and also giving feedback.</li>
</ul>
<p>The architectural diagram below shows the data flow and technical details of the app</p>
<p align="center"><img src="https://github.com/olala10/FieldWise/blob/main/resourse/FieldWise%20Architectural%20Diagram.png" width="1000"/></p>
<p align="center"><i><b>Figure 4</b> FieldWise Architectural Diagram</i></p>
