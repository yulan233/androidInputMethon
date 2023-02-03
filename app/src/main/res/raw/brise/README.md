<meta charset="UTF-8">

La brise
===
Rime schema repository

Project home
---
[rime.im](http://rime.im)

License
---
GPLv3

Individual packages in this collection can be released under different licenses.
Please refer to their respective LICENSE files.

Contents
===
This software is a collection of data packages used by [Rime](http://rime.im)
to support various Chinese input methods, including those based on modern
dialects or historical diasystems of the Chinese language.

A **Rime input schema** defines a specific input method in Rime's DSL.
It consists of a configuration file named `*.schema.yaml` where `*` is the
schema ID, and an optional **Rime dictionary** file named `*.dict.yaml`.

A package may contain one or several interrelated input schemata and their
affiliated Rime dictionaries.

Packages
===

/* TODO: implement this */ With `rime-cli` you can
```sh
rime install <package-name>
```
to add a package to your Rime configuration.

Essentials
---

  - `prelude`: the prelude package, providing Rime's default settings
  - `essay`: 八股文 / a shared vocabulary and language model

Phonetic-based input methods
---
Modern Standard Madarin

  - `luna-pinyin`: 朙月拼音 / Pinyin in Tranditional Chinese
  - `terra-pinyin`: 地球拼音 / School-taught Pinyin, with tone marks
  - `bopomofo`: 注音 / Zhuyin (aka. Bopomofo)
  - `pinyin-simp`: 袖珍簡化字拼音 / Pinyin in Simplified Chinese

Derivatives of Pinyin

  - `double-pinyin`: 雙拼 / Double Pinyin (ZiRanMa, ABC, flyPY, MSPY, PYJJ variants)
  - `combo-pinyin`: 宮保拼音 / Chord-typing Pinyin
  - `stenotype`: 打字速記法 / a stenographic system derived from ABC Easy Shorthand

Other modern varieties of Chinese

  - `jyutping`: 粵拼 / Cantonese
  - `wugniu`: 上海吳語 / Wu (Shanghainese)
  - `soutzoe`: 蘇州吳語 / Wu (Suzhounese)

Middle Chinese

  - `zyenpheng`: 中古漢語拼音 / Middle Chinese

Shape-based input methods
---

  - `stroke`: 五筆畫 / five strokes
  - `cangjie`: 倉頡輸入法 / Cangjie input method
  - `quick`: 速成 / Simplified Cangjie
  - `wubi`: 五筆字型
  - `array`: 行列輸入法
  - `scj`: 快速倉頡

Miscelaneous
---

  - `emoji`: 繪文字 / input emoji with English or Chinese Pinyin keywords
  - `ipa`: 國際音標 / International Phonetic Alphabet

Install
===

Build dependencies
---

- librime>=1.3 (for `rime_deployer`)

Run-time dependencies
---

  - librime>=1.3
  - opencc>=1.0.2

Build and install
---

```sh
make
sudo make install
```

Credits
===
We are grateful to the makers of the following open source projects:

  - [Android Pinyin IME](https://source.android.com/) (Apache 2.0)
  - [Chewing / 新酷音](http://chewing.im/) (LGPL)
  - [ibus-table](https://github.com/acevery/ibus-table) (LGPL)
  - [OpenCC / 開放中文轉換](https://github.com/BYVoid/OpenCC) (Apache 2.0)
  - [moedict / 萌典](https://www.moedict.tw) (CC0 1.0)
  - [Rime 翰林院 / Rime Academy](https://github.com/rime-aca) (GPLv3)

Also to the inventors of the following input methods:

  - Cangjie / 倉頡輸入法 by 朱邦復
  - Array input method / 行列輸入法 by 廖明德
  - Wubi / 五筆字型 by 王永民
  - Scj / 快速倉頡 by 麥志洪
  - Middle Chinese Romanization / 中古漢語拼音 by 古韻

Contributors
===
The repository is a result of collective effort. It was set up by the following
people by contributing files, patches and pull-requests. See also the
[contributors](https://github.com/rime/brise/graphs/contributors) page for a
list of open-source collaborators.

  - [佛振](https://github.com/lotem)
  - [Kunki Chou](https://github.com/kunki)
  - [雪齋](https://github.com/LEOYoon-Tsaw)
  - [Patrick Tschang](https://github.com/Patricivs)
  - [Joseph J.C. Tang](https://github.com/jinntrance)
  - [lxk](http://101reset.com)
  - [Ye Zhou](https://github.com/zhouye)
  - Jiehong Ma
  - StarSasumi
  - 古韻
  - 寒寒豆
  - 四季的風
  - 上海閒話abc
  - 吳語越音

Contributing
===
Pull requests are welcome for established, open-source input methods that
haven't been included in the repository. Thank you!
But you'll be responsible for providing source files along with an open-source
license because licensing will be rigidly scrutinized by downstream packagers.
