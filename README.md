# dai-lab-smtp
This program automatically sends premade emails to multiple groups of people. Given a .txt file with a list of emails, a .json file with the subjects and bodies of the emails and a number of groups, this program will form groups of 2-5 people selected from the list of emails and assign a message to each group. The first person selected in each group will be the 'sender' and an email is sent to everyone else in the group that will be seen as if it was sent from the designated sender.

### File formats
The .txt file containing the list of emails is simply formatted with one email per line
```
example1@domain1.com
example2@domain2.com
example3@domain3.com
```


The .json is formatted like this:


```json
[
    {"subject": "subject1", "body" : "message1"}, 
    {"subject": "subject2", "body" : "message2"}
]
```

### 