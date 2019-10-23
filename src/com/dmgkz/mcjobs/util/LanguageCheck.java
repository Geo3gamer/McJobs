/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmgkz.mcjobs.util;

import com.dmgkz.mcjobs.McJobs;
import com.dmgkz.mcjobs.playerjobs.PlayerJobs;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

/**
 *
 * @author Bl4ckSkull666
 */
public class LanguageCheck implements Runnable {
    private FileConfiguration _tmp = null;
    
    @Override
    public void run() {
        File fold = new File(McJobs.getPlugin().getDataFolder(), "languages");
        HashMap<String, FileConfiguration> tmp = new HashMap<>();
        tmp.putAll(McJobs.getPlugin().getLanguage().getLanguages());
        boolean changes = false;
        for(Map.Entry<String, FileConfiguration> me: tmp.entrySet()) {
            File f = new File(fold, me.getKey() + ".yml");
            if(!f.exists())
                return;
                
            _tmp = me.getValue();
            if(checkJobs("jobs")) {
                changes = true;
                save(f);
            }
            
            if(checkEnchants("enchant")) {
                changes = true;
                save(f);
            }
            
            if(checkPotions("potion")) {
                changes = true;
                save(f);
            }
            
            if(checkEntityTypes("entities")) {
                changes = true;
                save(f);
            }
            
            if(checkMaterials("materials")) {
                changes = true;
                save(f);
            }
            
            if(checkColors("color")) {
                changes = true;
                save(f);
            }
            
            if(checkLanguages("languages")) {
                changes = true;
                save(f);
            }
            
            if(checkOtherMessages()) {
                changes = true;
                save(f);
            }
            _tmp = null;
        }
        
        if(changes) {
            McJobs.getPlugin().reloadLanguages();
        }
    }
    
    private void save(File f) {
        try {
            _tmp.save(f);
        } catch(IOException ex) {
            McJobs.getPlugin().getLogger().warning("Error on save Language File " + f.getName() + " after add missing components.");
        }
    }
    
    private boolean checkJobs(String section) {
       boolean hasChanged = false;
        for(String job: PlayerJobs.getJobsList().keySet()) {
            if(!_tmp.isString(section + ".name." + job)) {
                _tmp.set(section + ".name." + job, job);
                hasChanged = true;
            }
            if(!_tmp.isString(section + ".description." + job.toLowerCase())) {
                _tmp.set(section + ".description." + job.toLowerCase(), "Missing description for Job " + job);
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkEnchants(String section) {
        boolean hasChanged = false;
        for(String ench: McJobs.getPlugin().getHolder().getEnchants().getEnchants().keySet()) {
            if(!_tmp.isString(section + "." + ench.toLowerCase())) {
                _tmp.set(section + "." + ench.toLowerCase(), ench.replace("_", " "));
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkPotions(String section) {
        boolean hasChanged = false;
        for(String pta: McJobs.getPlugin().getHolder().getPotions().getPotions().keySet()) {
            if(!_tmp.isString(section + "." + pta.toLowerCase())) {
                _tmp.set(section + "." + pta.toLowerCase(), pta.replace("_", " "));
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkEntityTypes(String section) {
        boolean hasChanged = false;
        for(EntityType et: EntityType.values()) {
            if(!_tmp.isString(section + "." + et.name().toLowerCase())) {
                _tmp.set(section + "." + et.name().toLowerCase(), et.name().replace("_", " "));
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkMaterials(String section) {
        boolean hasChanged = false;
        for(Material mat: Material.values()) {
            if(!_tmp.isString(section + "." + mat.name().toLowerCase())) {
                _tmp.set(section + "." + mat.name().toLowerCase(), mat.name().replace("_", " "));
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkColors(String section) {
        boolean hasChanged = false;
        for(DyeColor dc: DyeColor.values()) {
            if(!_tmp.isString(section + "." + dc.name().toLowerCase())) {
                _tmp.set(section + "." + dc.name().toLowerCase(), dc.name().replace("_", " "));
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean checkLanguages(String section) {
        boolean hasChanged = false;
        for(String lang: McJobs.getPlugin().getLanguage().getAvaLangs()) {
            if(!_tmp.isString(section + "." + lang.toLowerCase())) {
                _tmp.set(section + "." + lang.toLowerCase(), lang);
                hasChanged = true;
            }
        }
        return hasChanged;
    }
    
    private boolean issetString(String str, String msg) {
        if(!_tmp.isString(str)) {
            _tmp.set(str, msg);
            return false;
        }
        return true;
    }
    
    private boolean issetInt(String str, int i) {
        if(!_tmp.isInt(str)) {
            _tmp.set(str, i);
            return false;
        }
        return true;
    }
    
    private boolean checkOtherMessages() {
        HashMap<String, String> tmp = new HashMap<>();
        HashMap<String, Integer> tmpInt = new HashMap<>();
        
        tmp.put("jobscommand.playeronly", "Can only be done by a player!");
        tmp.put("jobscommand.permission", "&cYou do not have permission to do this!");
        tmp.put("jobscommand.args", "&cYou gave too many arguments!");
        tmp.put("jobscommand.exist", "&cThe job &6%j&c does not exist!");
        tmp.put("jobscommand.rejoin", "&7You can now rejoin the &6%j&7 job.");
        tmp.put("jobscommand.show", "&7Now &ashowing&7 pay messages for the &6%j&7 job.");
        tmp.put("jobscommand.hide", "&7Now &chiding&7 pay messages for the &6%j&7 job.");
        tmp.put("jobscommand.join", "&6You have learned the job %j successful.");
        tmp.put("jobscommand.nojob", "&cThe wished Job don''t exist.");
        tmp.put("jobscommand.donthave", "&cYou don''t have the job %j.");
        tmp.put("jobscommand.have", "&cYou have already the wished job.");
        tmp.put("jobscommand.language-header", "&ePress on the wished Language to change:");
        tmp.put("jobscommand.no-language", "&cCan't find the wished language &e%g&c.");
        tmp.put("jobscommand.language-changed", "&6Changed language successfull.");
        tmp.put("jobscommand.language-footer", "&eChange language with use /mcjobs language (language)");
        
        
        
        
        tmp.put("jobsnotify.and", "and");
        tmp.put("jobsnotify.hours", "hours");
        tmp.put("jobsnotify.hour", "hour");
        tmp.put("jobsnotify.minutes", "minutes");
        tmp.put("jobsnotify.minute", "minute");
        tmp.put("jobsnotify.days", "days");
        tmp.put("jobsnotify.day", "day");
        tmp.put("jobsnotify.weeks", "weeks");
        tmp.put("jobsnotify.week", "week");
        tmp.put("jobsnotify.months", "months");
        tmp.put("jobsnotify.month", "month");
        tmp.put("jobsnotify.message", "&aYour &6MC Jobs&a have earned you &6%j&a in the past &c%g&a.");
        tmp.put("jobsnotify.overpay", "&e%p&a, you are exhausted and cannot earn more from &6MC Jobs&a for awhile.");
        tmp.put("jobsdisplay.employed", "--   &cEmployed");
        tmp.put("jobsdisplay.unemployed", "-- &9Unemployed");
        tmp.put("jobsdisplay.default", "--    &3Default");
        tmp.put("jobsdisplay.basepay", "Base Pay");
        tmp.put("jobsdisplay.charge", "CHARGE");
        tmp.put("jobsdisplay.pay", "PAY");
        tmp.put("jobsdisplay.level", "LEVEL");
        tmp.put("jobsdisplay.rank", "RANK");
        tmp.put("jobsdisplay.exp", "XP");
        tmp.put("jobsdisplay.break", "BREAK");
        tmp.put("jobsdisplay.place", "PLACE");
        tmp.put("jobsdisplay.defeat", "DEFEAT");
        tmp.put("jobsdisplay.fishing", "FISHING");
        tmp.put("jobsdisplay.craft", "CRAFT");
        tmp.put("jobsdisplay.repair", "REPAIR");
        tmp.put("jobsdisplay.enchant", "ENCHANT");
        tmp.put("jobsdisplay.potion", "POTIONS");
        tmp.put("jobsdisplay.tier", "TIER");
        tmp.put("jobsdisplay.pvp", "PvP");
        tmp.put("jobsdisplay.shear", "SHEAR");
        tmp.put("jobsdisplay.pvptier", "Requires more than %g kills from same player");
        tmp.put("jobsdisplay.button.join", "&f[&eJoin &6%j&f]");
        tmp.put("jobsdisplay.button.leave", "&f[&cLeave &6%j&f]");
        tmp.put("jobsdisplay.button.info", "&f[%g%j&f]");
        tmp.put("jobsdisplay.button.language", "&f[&e%g&f]");
        
        tmp.put("jobsjoin.have", "You already have &6%j&c!");
        tmp.put("jobsjoin.join", "%p has joined &9%j&7.");
        tmp.put("jobsjoin.toomany", "&6%p&c you have too many jobs.  To join a new job:");
        tmp.put("jobsjoin.command", "/jobs leave [job]");
        tmp.put("jobsjoin.jobperm", "&6%p&7 You don''t have permission to join &9%j&7");
        tmp.put("jobsjoin.timer", "&7You cannot join &6%j&7 for another &c%g&7.");
        tmp.put("jobsleave.quit", "You have quit &6%j&7.");
        tmp.put("jobsleave.donthave", "You do not have the &6%j&c job.");
        tmp.put("jobsleave.leavedefault", "You cannot leave &6%j&c because it is a default job.");
        tmp.put("jobslist.available", "&e%p&2, available jobs are:");
        tmp.put("jobslist.jobsin", "Jobs in &cred&2 you already have.");
        tmp.put("jobslist.nojob", "Jobs in &8dark grey&2 are unavailable to you.");
        tmp.put("jobslist.defaultjob", "Jobs in &3dark aqua&2 are default jobs.");
        tmp.put("jobslist.specific", "To learn about a specific job type: &e/jobs [job name]");
        tmp.put("jobslist.jobs", "JOBS:");
        tmp.put("jobshelp.page", "PAGE");
        tmp.put("jobshelp.1.1", "&7   Welcome to &3MC Jobs&7.  &3MC Jobs&7 is a mod designed to allow you");
        tmp.put("jobshelp.1.2", "&7to earn money doing various activities on the server based");
        tmp.put("jobshelp.1.3", "&7upon the jobs you choose.");
        tmp.put("jobshelp.1.4", "");
        tmp.put("jobshelp.1.5", "&2   To get started you need to choose a job.  You can find");
        tmp.put("jobshelp.1.6", "&2what jobs are available by typing &e/jobs list &2into the chat box.");
        tmp.put("jobshelp.1.7", "");
        tmp.put("jobshelp.1.8", "&2Learn more about each job by typing into the chat box:");
        tmp.put("jobshelp.1.9", "&e/jobs [job_name]");
        tmp.put("jobshelp.1.10", "");
        tmp.put("jobshelp.1.11", "&e[job name] &2can be any job name, for example &6digger&2.");
        tmp.put("jobshelp.1.12", "&e/jobs join &2and&e leave &2are explained on the next page.");
        tmp.put("jobshelp.1.13", "--");
        tmp.put("jobshelp.2.1", "&2You can join a job by typing the command:");
        tmp.put("jobshelp.2.2", "&e/jobs join [job name]");
        tmp.put("jobshelp.2.3", "");
        tmp.put("jobshelp.2.4", "&2Joining a job will allow you to earn and lose money based upon");
        tmp.put("jobshelp.2.5", "&2the pay tables of each job.  You are only allowed to have a");
        tmp.put("jobshelp.2.6", "&2maximum of &c%j&2 jobs on this server.");
        tmp.put("jobshelp.2.7", "");
        tmp.put("jobshelp.2.8", "&2You can leave a job by entering &e/jobs leave [job name]");
        tmp.put("jobshelp.2.9", "&2Once you have the maximum number of jobs the server allows,");
        tmp.put("jobshelp.2.10", "&2the only way to choose new ones is to leave an existing job.");
        tmp.put("jobshelp.2.11", "");
        tmp.put("jobshelp.2.12", "&2To learn more about &e/jobs list&2 go to the next page.");
        tmp.put("jobshelp.2.13", "--");
        tmp.put("jobshelp.3.1", "&2   The list command at a quick glance gives useful");
        tmp.put("jobshelp.3.2", "&2information about what jobs are available to you and what");
        tmp.put("jobshelp.3.3", "&2jobs you cannot take.");
        tmp.put("jobshelp.3.4", "");
        tmp.put("jobshelp.3.5", "&2Jobs that you have are in &cred&2.  Jobs that you can take are in");
        tmp.put("jobshelp.3.6", "&6gold&2.  Jobs that are unavailable to you are in &8dark grey&2.");
        tmp.put("jobshelp.3.7", "");
        tmp.put("jobshelp.3.8", "&2Default jobs are in &3dark aqua&2.  They are jobs that are");
        tmp.put("jobshelp.3.9", "&2given to everyone logging into the server.  Only players with");
        tmp.put("jobshelp.3.10", "&2the &cmcjobs.admin.leavedefault&2 perm can quit &3default&2 jobs.");
        tmp.put("jobshelp.3.11", "");
        tmp.put("jobshelp.3.12", "&2Continue the &elist&2 command on the next page.");
        tmp.put("jobshelp.3.13", "--");
        tmp.put("jobshelp.4.1", "&2Lastly it tells you how many jobs you have versus the max");
        tmp.put("jobshelp.4.2", "&2jobs the server allows, which is &c%j&2 on this server.");
        tmp.put("jobshelp.4.3", "");
        tmp.put("jobshelp.4.4", "&2  The &e/jobs leave&2 command works the same as the &ejoin");
        tmp.put("jobshelp.4.5", "&2command.  You can only leave jobs that you have already");
        tmp.put("jobshelp.4.6", "&2joined.  This will allow you to pick up new jobs in case you");
        tmp.put("jobshelp.4.7", "&2change your mind on what you want to be.");
        tmp.put("jobshelp.4.8", "");
        tmp.put("jobshelp.4.9", "&2Once you leave a job you cannot rejoin it for a server");
        tmp.put("jobshelp.4.10", "&2specified time frame.  The default is 1 hour.");
        tmp.put("jobshelp.4.11", "");
        tmp.put("jobshelp.4.12", "&2To learn about &e/jobs [job name] continue to next page");
        tmp.put("jobshelp.4.13", "--");
        tmp.put("jobshelp.5.1", "&2  The &e/jobs [job name]&2 command gives the player all the");
        tmp.put("jobshelp.5.2", "&2information they need to know about each job.");
        tmp.put("jobshelp.5.3", "");
        tmp.put("jobshelp.5.4", "&2The first line starts with the &3jobs name&2 and is followed by");
        tmp.put("jobshelp.5.5", "&2whether you are &cemployed&2 by the job or whether you are");
        tmp.put("jobshelp.5.6", "&2not part of the job.  In which case it will say &9unemployed&2.");
        tmp.put("jobshelp.5.7", "&2Next it tells you the &abase pay&2 of the job.  This figure is how");
        tmp.put("jobshelp.5.8", "&2much money or xp &6TIER1&2 will pay you upon doing the &eaction&2.");
        tmp.put("jobshelp.5.9", "&2The last bit of information this line tells the player is whether");
        tmp.put("jobshelp.5.10", "&2the job &apays&2 or &ccharges&2 the player for completing &eactions&2.");
        tmp.put("jobshelp.5.11", "");
        tmp.put("jobshelp.5.12", "&2The next page will explain the subsequent lines of &e/jobs [job]");
        tmp.put("jobshelp.5.13", "--");
        tmp.put("jobshelp.6.1", "&2As said a job can both &apay&2 and &ccharge&2 the player.  As an");
        tmp.put("jobshelp.6.2", "&2example, the &6miner&2 job &apays&2 the player for breaking &7iron ore&2.");
        tmp.put("jobshelp.6.3", "&2However if you place &7iron ore&2 it will &ccharge&2 you money instead.");
        tmp.put("jobshelp.6.4", "");
        tmp.put("jobshelp.6.5", "&2If you are currently &cemployed&2 with the job it will show you");
        tmp.put("jobshelp.6.6", "&2your current &6level&2 and &6rank&2 in the job.");
        tmp.put("jobshelp.6.7", "&2The second line is the jobs &6description&2.");
        tmp.put("jobshelp.6.8", "");
        tmp.put("jobshelp.6.9", "&2After the dashes is what the jobs &eactions&2 are and what blocks");
        tmp.put("jobshelp.6.10", "&2or entities are used by the job.");
        tmp.put("jobshelp.6.11", "");
        tmp.put("jobshelp.6.12", "&2The next page will explain the job &eactions&2.");
        tmp.put("jobshelp.6.13", "--");
        tmp.put("jobshelp.7.1", "&2The actions are: &6BREAK&2, &6PLACE&2, &6DEFEAT&2, &6CRAFT&2, &6REPAIR&2,");
        tmp.put("jobshelp.7.2", "&6FISHING&2, &6POTIONS&2, &6ENCHANT&2, &6SHEAR and &6PVP&2.");
        tmp.put("jobshelp.7.3", "");
        tmp.put("jobshelp.7.4", "&6BREAK&2: Is the action of breaking blocks like &7dirt&2.");
        tmp.put("jobshelp.7.5", "&6PLACE&2: Is the action of placing blocks like &7stone stairs&2.");
        tmp.put("jobshelp.7.6", "&6DEFEAT&2: Is the action of killing monsters like &7spiders&2.");
        tmp.put("jobshelp.7.7", "&6CRAFT&2: Is the action of crafting items like &7wood sword&2.");
        tmp.put("jobshelp.7.8", "&6REPAIR&2: Is the action of repairing items like &7stone shovel&2.");
        tmp.put("jobshelp.7.9", "&2This covers both the &6mcMMO&2 repair and the vanilla repair.");
        tmp.put("jobshelp.7.10", "&6FISHING&2: &aPays&2 or &ccharges&2 whenever you use a fishing rod to");
        tmp.put("jobshelp.7.11", "&2catch an entity or fish.");
        tmp.put("jobshelp.7.12", "&6Potion&2 and &6Enchants&2 are covered on the next page.");
        tmp.put("jobshelp.7.13", "--");
        tmp.put("jobshelp.8.1", "&6POTIONS&2: &aPays&2 or &ccharges&2 for using a brew stand to make");
        tmp.put("jobshelp.8.2", "&2potions.");
        tmp.put("jobshelp.8.3", "&6ENCHANT&2: &aPays&2 or &ccharges&2 when the player enchants an item.");
        tmp.put("jobshelp.8.4", "&6SHEAR&2: &aPays&2 or &ccharges&2 when the player shear a sheep.");
        tmp.put("jobshelp.8.5", "&6PVP&2: &aPays&2 or &ccharges&2 when the player kills other player.");
        tmp.put("jobshelp.8.6", "");
        tmp.put("jobshelp.8.7", "");
        tmp.put("jobshelp.8.8", "");
        tmp.put("jobshelp.8.9", "&eEnglish&c localization done by: &6RathelmMC");
        tmp.put("jobshelp.8.10", "");
        tmp.put("jobshelp.8.11", "");
        tmp.put("jobshelp.8.12", "&7More info at: &6 https://dev.bukkit.org/projects/mcjobs");
        tmp.put("jobshelp.8.13", "--");
        tmp.put("jobshelp.continuepage", "&7Continue on Page %g");
        tmp.put("jobshelp.prevpage", "&f[&6Previos page&f]");
        tmp.put("jobshelp.nextpage", "&f[&6Next page&f]");
        tmp.put("jobshelp.command", "&6/jobs help %g");
        tmp.put("jobshelp.finish", "&7END OF HELP FILE");
        tmp.put("jobshelp.nohelp", "&7%g&c is not a help page!");
        tmp.put("admincommand.permission", "&6%p&c you do not have permission to do this!");
        tmp.put("admincommand.failedreload", "&cFailed to reload the plugin!");
        tmp.put("admincommand.succeedreload", "&6MC Jobs&c has been reloaded!");
        tmp.put("admincommand.defaults", "&7Defaults have been added to the config.yml file.");
        tmp.put("admincommand.args", "&cToo many arguments for &e/jadm&c.  So this is the end, we''re going to test a ridiculously long string.");
        tmp.put("admincommand.exist", "&6%j&c does not exist!");
        tmp.put("adminadd.args", "&cWrong arguments: &e/jadm add [player/group] [job_name]");
        tmp.put("adminadd.offline", "&6%p&c group doesn''t exist or isn''t a player that has been seen!");
        tmp.put("adminadd.novault", "&6%p&c is not a known player!");
        tmp.put("adminadd.hasjob", "&6%p&7 already has the &9%j&7 job.");
        tmp.put("adminadd.empty", "&7There are no &eplayers&7 online in the &6%p&7 group.");
        tmp.put("adminadd.added", "&6%p&7 has been added to &9%j&7.");
        tmp.put("adminadd.padded", "&6%p&2 you have been added to the &9%j&2 job by a server admin.");
        tmp.put("adminremove.args", "&cWrong arguments: &e/jadm remove [player/group] [job_name]");
        tmp.put("adminremove.nojob", "&6%p&7 does not have the &9%j&7 job.");
        tmp.put("adminremove.nodefault", "&6%p&c you do not have permission to remove &3default&c jobs!");
        tmp.put("adminremove.removed", "&6%p&7 has been removed from &9%j&7.");
        tmp.put("adminremove.premoved", "&6%p&2 you have been removed from the &9%j&2 job by a server admin.");
        tmp.put("adminlist.args", "&cWrong arguments: &e/jadm list [player/group]");
        tmp.put("adminlist.playerlist:", "&2%p&7: %g");
        tmp.put("adminlist.nojobs", "&2%p&c has no jobs.");
        tmp.put("adminlist.wrongpage", "&e%g is not a proper page number.  Using page 1 instead.");
        tmp.put("pitch.line0", "This server runs:");
        tmp.put("pitch.line1", "&aTo see what jobs are available type. &e/jobs list");
        tmp.put("pitch.line2", "&aTo join a job type. &e/jobs join [job_name]");
        tmp.put("pitch.line3", "&aPlay, &2earn money&a, and have fun!");
        tmp.put("onadminlogin.toolow", "&eYour pay scale is too low for an &6XP&e based economy.  Consider switching &2pay_scale&e to high in the config.yml file.");
        tmp.put("onadminlogin.outofdate", "&eConfig.yml is out of date.  &6MC Jobs&e may not work properly without reloading the config file.");
        tmp.put("experience.level", "&eYou are now level &6%g&e in &6%j&e.");
        tmp.put("experience.reset", "&eYour level in &6%j&e has been reset to &c0&e.");
        tmp.put("experience.rank", "&eYou are now rank &6%g&e in &6%j&e.");
        tmp.put("experience.added_lvl", "&7You have given &6%p &a%g&7 levels in &6%j&7.");
        tmp.put("experience.padded_lvl", "&7You have been given &a%g&7 levels in &6%j&7 by a system admin.");
        tmp.put("experience.added_xp", "&7You have given &6%p &a%g&7 experience in &6%j&7.");
        tmp.put("experience.padded_xp", "&7You have been given &a%g&7 experience in &6%j&7 by a system admin.");
        tmp.put("experience.nojob", "&6%p&c doesn''t have the &6%j&c job.");
        tmp.put("payment.pay", "&aThe &6%j&a job has paid you &6%g&a %p.");
        tmp.put("payment.payxp", "&aThe &6%j&a job has earned you &6%g&a experience.");
        tmp.put("payment.charge", "&aThe &6%j&a job has cost you &c%g&a %p.");
        tmp.put("payment.chargexp", "&aThe &6%j&a job has taken &c%g&a experience.");
        tmp.put("payment.currency_single", "dollar");
        tmp.put("payment.currency_plural", "dollars");
        tmp.put("languages.en", "English");
        tmpInt.put("spaces.jobslist", 16);
        tmpInt.put("spaces.display", 4);
        tmpInt.put("spaces.displaytwo", 8);
        tmpInt.put("spaces.displaythree", 4);
        tmpInt.put("spaces.chargelen", 7);
        tmpInt.put("spaces.numhelp" , 8);
        
        boolean hasChanges = false;
        for(Map.Entry<String, String> me: tmp.entrySet()) {
            if(!issetString(me.getKey(), me.getValue())) 
                hasChanges = true;
        }
        
        for(Map.Entry<String, Integer> me: tmpInt.entrySet()) {
            if(!issetInt(me.getKey(), me.getValue())) 
                hasChanges = true;
        }
        
        return hasChanges;
    }
}
