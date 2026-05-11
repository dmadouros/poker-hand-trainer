# Limit Hold 'em Starting Hand Trainer

This is a limit hold'em poker hand trainer based on recommendations from [_Getting Started in Hold 'em_ by Ed Miller](https://a.co/d/0ipEkE8q)

You will be dealt a series of hands. Given your hand, your starting 
position, and any action that has taken place ahead of you, you will be asked what your 
action should be (raise, check, bet, call, fold). The application will 
then take the same information and compare your answer to the correct 
answer. You get 1 point if your answer is correct and 0 points if your 
answer is incorrect.

And the end of the series of hands, you will be shown your total score 
and some basic statistics:
- what positions you were given
- what previous action you were given
- what actions you took

Mostly these stats are for debugging purposes. Specifically, the 
"previous actions" as these are weighted for a game with little pre-flop 
raising.

Good luck; have fun!

To run the application:

`./run.sh`

## Dev Notes

### Problems I Ran Into

1. If `run.sh` is not executable, try: `chmod u+x run.sh`
2. I use `iTerm2` and the font I was using (Inconsolata Nerd Font) used a 
different (and much worse) &hearts; icon than I wanted. To fix this, I 
switched to using the Menlo font during development.

### Miscellaneous

- In theory, this application works with any training set of opening hand 
data. You would need to replace the contents of the `rows` attribute in 
`me.dmadouros.pokerhand.infrastructure.Database`. It was a PITA to 
manually transform this data from the structure provided in the book to 
the data structure used here and if someone (maybe me) wanted to spend 
sometime writing a better translation mechanism from the book's notation 
system to how it is stored in the table or just a better data structure, 
this could be a fun project for another time.
- I opted to use [Mordant](https://github.com/ajalt/mordant) as a simpler 
approach to a text-based application. Mostly, I wanted to have something 
that would easily clear the screen in a built-in way. I'm not 100% 
certain Mordant satisfied my desire. It has some cool features. Most of
which I never used. Despite what Google's AI told me, Mordant does not 
have a built-in `clearScreen` method. It does have a way to do it, but 
it's rather verbose and I ended up implementing an extension function to 
get what I wanted. The only other feature I took advantage of was the 
library's `prompt` feature. This allowed me to ask the user for some 
input and has built-in validation and hint text, so I went with it. But, 
I think I could have pretty easily written this on my own. Maybe someday, 
I'll use more of Mordant's capabilities. Or, maybe I'll just rip it out.
- I'm really proud of the way that the domain classes came out. However, 
I didn't put any effort into separating the UI from the application or 
the domain. If someone was looking for ways to improve this, this is an 
area that could use it. At the moment, it doesn't seem worth it. But, if 
someone wanted to create a different UI (e.g. a javascript front end that 
ran in a browser), the 'need' for this separation increases. Maybe 
someday. 