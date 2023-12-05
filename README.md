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
  { "subject": "subject1", "body": "message1" },
  { "subject": "subject2", "body": "message2" }
]
```

Files are read through static functions from a FileReader class and .json is parsed using the Gson library.

### Implementation

![Alt text](./media/smtp_classes.jpg)

Emails are sent by groups and each Group object contains all the information necessary to send an email.

Group object are constructed with a sender, a list of victims and a message. The amount of people per group (2 minimum and 5 maximum) is determined by the amount of groups asked for and the amount of emails provided. All groups will try to have as many people as possible while keeping the same amount in every group with no overlap. Any remaining email adresses that can't be fit in groups is ignored. An exception is thrown if the amount of emails is too few or if an erroneous adress is detected in the file. Messages are assigned to each group in their order in the .json file (if there are too few messages, we go back to the start of the list to assign them).

Once a group is made an SmtpEmailSender is constructed for each group, which handles connecting to the smtp server and sending all the emails for their group. For each distinct domain in the list of victims, an smtp client is simulated through the SmtpClient class to send the group's message (the same exact message is sent to all victims in the group).

The SmtpClient class handles the dialogue with the server. If the server responds with an error message, an exception is thrown.
