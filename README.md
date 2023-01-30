# pandoc-spiceland 🥘

This is a build tool/repo/thing using [pandoc](https://pandoc.org/) meant to help typeset LuaLaTeX articles in various South Asian languages. 

## Get Started
Here is a list of things you will need to have installed before using this project.

- [babashka](https://github.com/babashka/babashka#quickstart) (to run `make.clj`)
- [TeXLive](https://www.tug.org/texlive/) (to actually typeset the document)
- [Pandoc](https://pandoc.org/installing.html) (to convert the Markdown to LuaLaTeX)

Once all that is downloaded, run `./setup.clj`, as that will set up the local environment for `babel`.

## Getting Started/Usage

Once you've figured out what language and script you want to write in, copy the appropriate file from `examples/` into the project root.
For example, if you're writing in Meitei, copy `examples/meetei.md` to the project root as `document.md` and modify it with your text content. Then run 
```sh
./make.clj :lang meetei :in document.md :out document.pdf
```

### Side note:
Because the script uses babashka, you have the option of doing this: 

```sh
./make.clj :lang <lang> :in <in-file> :out <out-file>
```

or this:

```sh
./make.clj --lang <lang> --in <in-file> --out <out-file>
```

```sh
./make.clj -L <lang> -i <in-file> -o <out-file>
```

Here `<lang>` refers to any of the [supported languages](#support).

## Goals

This project is also meant to help people create professional works in their own native language simply by using Pandoc-compatible Markdown rather than using LaTeX or Word. 

Another specific goal with this is to help speakers of minority languages, namely indigenous languages in South Asia, create professional literary works in their language with ease.

### Ideas that could be implemented
- XDG File Directory support for Unix
  - This would mean making the executable standalone
- Adding support for other pandoc supported markup file structures
- Adding ConTeXt support and templates.
- Make a markdown/pandoc editor

## Support

The language tag format usually goes by `<language>/<script>` but there can and probably will be exceptions.
The following are lists of language tags you can pass into `--lang`:

- `ahom` (Tai Ahom)
- `aiton` (Tai Aiton)
- `assamese`
- `bangla`
- `bhojpuri/kaithi`
- `bhojpuri/devanagari`
- `chakma`
- `dhivehi`
- `dogri`
- `gondi/gunjala`
- `gondi/masaram`
- `gujarati`
- `hindi`
- `ho`
- `kannada`
- `kashmiri/nastaliq`
- `kashmiri/sharada`
- `lepcha`
- `limbu`
- `maithili/mithilakshar`
- `maithili/devanagari`
- `malayalam`
- `marathi`
- `meitei` (Manipuri)
- `mru`
- `nepali`
- `newari/devanagari`
- `newari/newa`
- `odia`
- `pashto`
- `punjabi/gurmukhi`
- `punjabi/shahmukhi`
- `santali`
- `saraiki`
- `saurashtra`
- `sindhi/khudawadi`
- `sindhi/naskh`
- `sinhala`
- `sora`
- `syloti`
- `tamil`
- `tangsa`
- `telugu`
- `tibetan`
- `toto`
- `urdu`
- `western-pahari`
- `zomi`

You may notice that not everything is translated (e.g. the word "Contents" may show up as "?contents?"), but I would appreciate anyone who could help provide translations and localizations for the `.ini` files in `locale/`.

### Languages/Scripts that don't have Unicode or font support
Currently not yet supported in no particular order (but would be 

- `tulu` language and `tulu-tigalari` script (no Unicode support)
- `tolong siki` script (no Unicode support)
- `kurukh banna` script (no Unicode support)
- `achik-tokbirim` script
- ...and many more

## FAQ

### Why?

Professional typesetting for non-latin based scripts has been relatively inaccessible for a lot of writers in South Asia, and this was born from another project where I wanted to be able to generate professional PDFs in the languages of the Indian subcontinent with minimal configuration. 

Another reason is that Adivasi/indigenous and other minority languages of South Asia have often been overlooked and often have a lack of reading material and resources, and so hopefully this makes it easier to at least publish/write material for more people. 

### I'm seeing `?contents?` instead of the "Contents" or the proper translations for the word.

Unfortunately, right now not everything is translated, so that is something that is bound to show up at some point. In fact, some of the words that have already been translated in the babel repository may be incorrect. See

### How do I turn off the Indic numerals in case I don't want it?

Let's take Sora Sompeng as an example. In `langs/sora/prelude.md` we have:

```yaml
---
lang: srb
babel-otherlang: sora
babeloptions:
- import=srb
- maparabic
mainfont: Noto Sans Sora Sompeng
mainfontoptions:
- Extension=.ttf
- Path=scripts/sora-sompeng/fonts/
- UprightFont=*-Regular
- BoldFont=*-Bold
- Renderer=HarfBuzz
- Script=Sora Sompeng
---
```

Simply comment out either the line with either `maparabic` or `mapdigits`. Note that several languages like Ahom, Tamil, Malayalam, Kannada, Telugu, Sinhala, and Tibetan have this featured off by default. 

```yaml
---
lang: srb
babel-otherlang: sora
babeloptions:
- import=srb
# - maparabic
mainfont: Noto Sans Sora Sompeng
mainfontoptions:
- Extension=.ttf
- Path=scripts/sora-sompeng/fonts/
- UprightFont=*-Regular
- BoldFont=*-Bold
- Renderer=HarfBuzz
- Script=Sora Sompeng
---
```

### My language isn't listed on there?

Create an issue and I'll probably do a walk through on how to "create" your own language using this mechanism. But I am working to include as many of the subcontinental languages, so bear with me. 

### How do I change the font being used?

Place your fonts in the corresponding `scripts` folder. For example, Tamil fonts should go in `scripts/tamil/fonts/`. Then, in your document at the top among the various other settings:

```yaml
mainfont: <font_name>
mainfontoptions:
- Extension= <extension>
- Path=scripts/tamil/fonts/
- UprightFont= <upright-font> pattern
- ItalicFont= <italic-font> pattern
- Renderer=HarfBuzz
- Script=Tamil 
```

A couple of notes here:
- `<extension>` refers to the actual file extension
- `UprightFont` and `ItalicFont` match the file names of the various upgright and other fonts
  - So if your regular font name for example is named `TiroTamil-Regular.ttf`, then put `*-Regular`, and if your italic font name is `TiroTamil-Italic.ttf`, add `*-Italic` to ItalicFont
- `Script` should match the script name if possible


## How can I help?

#### If you're a typographer: 
We would really appreciate fonts that are tailored for the various indigenous scripts like Sora, Santali, Takri, Mundari Bani, Gunjala and Masaram Gondi.

#### If you speak any of the languages supported here:
We would really appreciate translation and localization help. It would go a long way towards helping further localize documents published in these languages. 

#### If you're a software dev:
If you figure you can streamline this even more, share your ideas and PRs.

If you want to help in anyway, you can create an issue and I will very likely look at it.