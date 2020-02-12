Grant Marsh

1) I changed the format of my Question class to allow for two different types of question, a text response question and a multiple choice question.
By allowing two types of questions, I changed the true false buttons from sending boolean expressions to strings that say true or false, so that the text box
and the true false questions had the same structure. After this, I simply had to link all of the xml formats and ID's to the corresponding question types and change the check answer
and update question functions to reflect multi-type questions.

2) It did change how I set up my controller because I only had 2 different question constructors. I did not use inheritance.

3) My only issue relaying data was finding out how to read the text within the EditText box in the xml... I tried SO many different ways.

4) The ViewModel handled the question creation, the getters of all of the question components, checking for correctness and moving across all of the questions