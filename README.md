
toolracklib
===============
A custom tool mod library in the vein of SilentGear, Tinker's Construct and Tetra, which aims to meet the strengths of each by changing its approach.

This library is designed with extensibility in mind, in every direction -- materials, part types (and thus tools -- read "Philosophy" below), traits, and even custom crafting interfaces will be possible on this mod. Documentation on how to do it will follow as the library reaches functional stages. Be warned, this library alone will offer about as complete an experience as Valkrien Skies alone does, in that it borderline doesn't, but we have plans to develop datapacks and addons to meet our goals stated above and below that will cure this.


Philosophy:
============
Our goal with this mod is to meet the strengths of our three inspirations, without sacrificing interoperability, or creating design conflicts, by approaching the idea somewhat differently at a basic level. Below follows what we see as the strengths of each, and our plans to mirror them.

As per SilentGear, datapack material support and addon support for custom part types, traits and other possible aspects is planned out of the box. Further, we intend to capture SilentGear's simplicity and low commitment, by maximising freedom to customise and recustomise -- tools can be freely taken apart and rebuilt at a table with no penalty, as to encourage experimentation and hands-on learning of the mod. Additionally, this library exclusively implements base tool logic and the framework for materials and parts, allowing for the full broader experience to be changed, including the overhead in even making a tool -- giving the option between a simple one-or-two-block solution, or:

Something more akin to Tinker's Construct's multiblocks (hopefully) in a future expansion. This is the one mod we're not confident in our ability to fully sidegrade with, as the mod largely succeeds on simply the sheer aesthetic and utility of the molten metal system. we hope, however, that in maximising versatility in our own case, we stand to provide something similarly interesting to players that want it, or even -- upon Tinker's updating to our version -- producing a compat so our tools can be made in the Tinker's Construct way, taking the best of both worlds. Additionally, Tinker's Construct also shows an excellent example of guiding player learning, by pacing mechanics alongside vanilla and mod progression, and providing strong in game documentation to back it up, an act we intend to follow -- though, hopefully, without so much player homework.

Finally, we hope to capture the depth of Tetra's system -- *without* the impenetrable complexity. To this end, we do not feature strictly defined tools at *all* -- rather, opting for tools to be emergent from the parts used in them, which define how they each fit together. You could make a pickaxe from a handle and a pickaxe head... or you could use a dual head, with a pickaxe head part on one side and an axe on the other, and a shovel attached to the base of the handle -- but you'd be giving up sheer stats in the process by not using those slots for extras.

This is an ambitions project that has no guarantee of seeing the light of day, but as of writing, good progress is being made. The modular design allows for focusing on basics first -- this library, a vanilla-based material pack and part addons, and a basic mod for using it in a game. *If* this goes well, we hope to develop further addons for compatibility with popular (and personal favourite) mods, as well as further on front-facing mods to provide more complex options for engaging in the tool-making process.

About the License:
==========
Anyone reading this particular file is likely aware of some of the mess arout licensing in this environment, I'm sure. First off, there is an additional permission (under GPLv3 section 7) for "yes this is a minecraft mod yes let people redistribute it and its source as such", so (I think) I'm not breaking any rules. If I am, and you represent Mojang, please yell at me. If one or none of these things apply, please don't.

That aside, IF YOU ARE ADDING TO THIS MOD, UNDERSTAND THE LICENSE. 

I should specify, we do not intend to be particularly litigious -- mostly because *why would I*, this is mostly here over LGPL on principle, partly personal precedent, partly as a cover from anyone that wants to use my work contract against me, and a little on the offchance that some larger rights-reserved or source-available (not naming names) project actually does use my project in a way that takes away user freedoms. ...though if you do directly modify toolracklib, publish that and then don't publish the source code I will be very upset.

As a short primer for use, though, remember -- and please defer to the Actual License (and Free Software Foundation's summaries of it), this primer is oversimplified, innacurate and NOT BINDING -- works extending this library must be under the same license (there's some specific rules about additional permissions and restrictions, which GO READ THE LICENSE). this applies REGARDLESS OF PACKAGING so datapacks and addons ARE COVERED BY THIS. 

there is an additional permission to distribute (convey) this mod alongside others as part of a modpack even if they are not of a compatible  (excluding specifically Essential -- use e4mc or another alternative please I bey you), but the wider work is still bound by the terms -- simply with permission to convey the work even if you can't redistribute a work unrelated to this mod but otherwise included in the pack under those terms. basically, if you create something that on its own would invoke the license, it still does, but if you use something that is already under a rights-reserved or other non-permissive license (which still allows you to use the mod in a modpack), you can still use it under that license in accord to its own rules, without publishing that part's source code. the pack itself is still AGPLv3, and as such so is anything made specifically for the pack -- but you would theoretically be able to produce something that otherwise wouldn't have to be AGPL, license it as you see fit and *then* add it to the pack as per this permission and be in compliance. I certainly would not care enough about niche cases like that to stop you, as much as I encourage anyone to release under copyleft on the same principles that the Free Software Foundation sets out.


The License (whoops):
===========
Copyright (C) 2026 [zincOctober]

This file is a part of toolracklib.

toolracklib is free software; you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.

toolracklib is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with toolracklib; if not, see <https://www.gnu.org/licenses>.

Additional permissions under GNU GPL version 3 section 7

If you modify toolracklib, or any covered work, by linking or combining it with Minecraft and/or its dependencies (or a modified version of those works), containing parts covered by the terms of the applicable licenses, the licensors of toolracklib grant you additional permission to convey the resulting work.

If you modify toolracklib, or any covered work, by linking or combining it with other libraries that would commonly be described as and are published as a "Minecraft mod" in that it creates additional functionality for Minecraft outside of Minecraft's own functionality, with the exception of the Essential mod by SparkUniverse, containing parts covered by the terms of otherwise non-compatible licenses, as part of a project describable as and published as a "Minecraft modpack", the licensors of toolracklib grant you additional permission to convey the resulting work.

